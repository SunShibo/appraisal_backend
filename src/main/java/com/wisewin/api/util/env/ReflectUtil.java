package com.wisewin.api.util.env;

import org.apache.commons.lang.ClassUtils;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * java反射工具，如：获取类信息、动态调用等
 *
 * @author Shibo Sun
 */
public class ReflectUtil extends ReflectionUtils {

    public static Field getField(Object target, String fieldName) {
        if (AopUtils.isAopProxy(target)) {
            target = AopUtils.getTargetClass(target);
        }

        Field field = findField((target instanceof Class) ? (Class<?>)target : target.getClass(), fieldName);
        makeAccessible(field);

        return field;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Object target, String fieldName, Class<T> requireType) {
        return (T) getFieldValue(target, fieldName);
    }

    public static Object getFieldValue(Object target, String fieldName) {
        Field field = getField(target, fieldName);
        makeAccessible(field);
        return getField(field, target);
    }

    public static void setFieldValue(Object target, String fieldName, Object value) {
        Field field = getField(target, fieldName);
        makeAccessible(field);
        setField(field, target, value);
    }

    public static void setField(Field field, Object target, Object value) {
        if (AopUtils.isAopProxy(target)) {
            target = getTarget(target);
        }

        try {
            field.set(target, value);
        } catch (IllegalAccessException ex) {
            handleReflectionException(ex);
            throw new IllegalStateException(
                    "Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
        }
    }
    public static Object getField(Field field, Object target) {
        if (AopUtils.isAopProxy(target)) {
            target = getTarget(target);
        }

        try {
            return field.get(target);
        } catch (IllegalAccessException ex) {
            handleReflectionException(ex);
            throw new IllegalStateException("Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static Object getTarget(Object proxy) {
        if(!AopUtils.isAopProxy(proxy)) {
            return proxy;
        }

        try {
            if(AopUtils.isJdkDynamicProxy(proxy)) {
                return getJdkDynamicProxyTargetObject(proxy);
            } else {
                return getCglibProxyTargetObject(proxy);
            }
        } catch (Exception ex) {
            handleReflectionException(ex);
            throw new IllegalStateException(
                    "Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);
        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        Object target = ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();

        return target;
    }

    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);
        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        Object target = ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();

        return target;
    }

    public static Object invokeMethod(Object obj, String methodName, Object[] args) {
        return invokeMethod(obj, methodName, null, args);
    }

    public static Object invokeMethod(Object obj, String methodName, Class<?>[] argsClasses, Object[] args) {
        Class<?> objClass = (obj instanceof Class) ? (Class<?>)obj : obj.getClass();
        if (argsClasses == null) {
            if (args != null && args.length > 0) {
                argsClasses = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    argsClasses[i] = args[i].getClass();
                }
            }
        }

        try {
            if (argsClasses == null || argsClasses.length == 0) {
                Method objMethod = objClass.getDeclaredMethod(methodName);
                objMethod.setAccessible(true);
                return objMethod.invoke(obj);
            } else {
                Method objMethod = objClass.getDeclaredMethod(methodName, argsClasses);
                objMethod.setAccessible(true);
                return objMethod.invoke(obj, args);
            }
        } catch (Exception e) {
            ReflectionUtils.handleReflectionException(e);
            throw new IllegalStateException(
                    "Unexpected reflection exception - " + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /**
     * 获取class类中常量字段，转换为map
     *
     * @param className
     * @return
     */
    public static Map<String, Object> getClassConstants(String className) {
        Map<String, Object> constants = new HashMap<String, Object>();

        Class<?> clazz = null;
        try {
            clazz = ClassUtils.getClass(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }

        try {
            Field[] fields = clazz.getFields();
            for (Field field : fields) {
                int modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
                    Object value = field.get(null);
                    if (value != null) {
                        constants.put(field.getName(), value);
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return constants;
    }
}
