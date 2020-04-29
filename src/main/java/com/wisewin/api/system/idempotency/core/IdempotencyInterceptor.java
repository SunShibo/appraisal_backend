package com.wisewin.api.system.idempotency.core;

import com.wisewin.api.system.idempotency.Null;
import com.wisewin.api.system.idempotency.enums.StrategyEnum;
import com.wisewin.api.system.idempotency.ex.IdempotencyIllegalArgsException;
import com.wisewin.api.system.idempotency.invoke.InvokeContext;
import com.wisewin.api.system.idempotency.invoke.InvokeMeta;
import com.wisewin.api.system.idempotency.strategy.IdempotentStrategy;
import com.wisewin.api.system.idempotency.strategy.LockWaitStrategy;
import com.wisewin.api.system.idempotency.strategy.OnlyCacheStrategy;
import com.wisewin.api.util.MD5Util;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Shibo on 16/8/29.
 */
public class IdempotencyInterceptor implements MethodInterceptor {

    /** 幂等性参数下标位置 */
    public static final int ARGS_INDEX_DEFAULT = 0;

    private volatile boolean enable = true;

    /** 默认策略 */
    private StrategyEnum defaultStrategy = StrategyEnum.LOCK_WAIT;
    private Map<StrategyEnum, IdempotentStrategy> strategies = new HashMap<StrategyEnum, IdempotentStrategy>();
    private ConcurrentHashMap<Method, InvokeMeta> invokeMetaCache = new ConcurrentHashMap<Method, InvokeMeta>();

    public IdempotencyInterceptor() {
        strategies.put(StrategyEnum.ONLY_CACHE, new OnlyCacheStrategy()) ;
        strategies.put(StrategyEnum.LOCK_WAIT, new LockWaitStrategy()) ;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        if (!enable) { // failover
            return invocation.proceed();
        }

        Object target = invocation.getThis();
        Method method = invocation.getMethod();
        Object[] args = invocation.getArguments();

        Idempotent idempotence = AnnotationUtils.findAnnotation(method, Idempotent.class);

        InvokeMeta invokeMeta = extractInvokeMeta(method , idempotence);
        IdempotentStrategy strategy = invokeMeta.getStrategy();
        String xid = this.extractIdempotentId (args , idempotence) ;

        InvokeContext invokeContext = new InvokeContext();
        invokeContext.setInvokeMeta(invokeMeta);
        invokeContext.setTarget(target);
        invokeContext.setArgs(args);
        invokeContext.setXid(xid);

        Object returnValue = null;
        try {
            returnValue = strategy.before(invokeContext);
            if (returnValue != null) {
                if (returnValue instanceof Null) {
                    return null;
                } else {
                    return returnValue;
                }
            }

            returnValue = invocation.proceed();

            strategy.afterReturning(invokeContext, returnValue);
        } catch (Exception e) {
            strategy.afterThrowing(invokeContext, e);
        } finally {
            strategy.after(invokeContext);
        }
        return returnValue;

    }


    private InvokeMeta extractInvokeMeta(Method method , Idempotent idempotence) {
        InvokeMeta invokeMeta = invokeMetaCache.get(method);
        if (invokeMeta == null) {
            invokeMeta = new InvokeMeta();
            invokeMeta.setMethod(method);
            invokeMeta.setMethodMd5(MD5Util.digest(method.toGenericString()));

            if (idempotence != null) {
                invokeMeta.setStrategy(getStrategy(idempotence.strategy() != null ? idempotence.strategy() : defaultStrategy));
                invokeMeta.setIdempotenceAnnotation(idempotence);
            }
            else {
                invokeMeta.setStrategy(getStrategy(defaultStrategy));
            }

            if (invokeMeta.getStrategy() == null) {
                throw new IllegalArgumentException("strategy config error");
            }

            invokeMetaCache.putIfAbsent(method, invokeMeta);
        }

        return invokeMeta;
    }

    private String extractIdempotentId (Object[] args , Idempotent idempotence) throws IdempotencyIllegalArgsException {
        int idemIdIndex = idempotence != null ? idempotence.idemIdIndex() : ARGS_INDEX_DEFAULT ;
        if (args == null || args.length < idemIdIndex + 1 || !(args[ARGS_INDEX_DEFAULT] instanceof String)) {
            throw new IdempotencyIllegalArgsException("幂等性非法参数异常!") ;
        }
        return (String)args[ARGS_INDEX_DEFAULT] ;
    }

    public IdempotentStrategy getStrategy(StrategyEnum name) {
        return strategies.get(name);
    }


}


