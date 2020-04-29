package com.wisewin.api.system.idempotency;

import com.wisewin.api.system.idempotency.core.Idempotent;
import com.wisewin.api.system.idempotency.enums.StrategyEnum;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Created by Shibo on 16/8/29.
 */
@Service("idempotencyService")
public class IdempotencyService {

    @Idempotent(idemIdIndex = 0 , strategy = StrategyEnum.LOCK_WAIT)
    public String aopTest (String str) {
        String tmp = "aop is running. param:" + str ;
        System.out.println(tmp);

        return tmp;
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext( "/conf/spring/application-context.xml");
        IdempotencyService is = (IdempotencyService)ctx.getBean("idempotencyService") ;
        System.out.println(is.aopTest("xxxxxxxxxxxccccccsfeseccffsdfsdfsfdd"));
    }

}
