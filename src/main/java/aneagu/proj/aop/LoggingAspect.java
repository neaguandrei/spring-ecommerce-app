package aneagu.proj.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    private long start;
    private long end;

    @Before("@annotation(aneagu.proj.aop.EnteringExitingLog)")
    public void enteringMethod(JoinPoint joinPoint) {
        logger.info("Entering method {} in {}", joinPoint.getSignature().getName(), joinPoint.getSourceLocation().getWithinType().getName());
        start = System.currentTimeMillis();
    }


    @After("@annotation(aneagu.proj.aop.EnteringExitingLog)")
    public void exitingMethod(JoinPoint joinPoint) {
        end = System.currentTimeMillis();
        logger.info("Exiting method {} in {} with duration {} milliseconds.",
                joinPoint.getSignature().getName(), joinPoint.getSourceLocation().getWithinType().getName(), (end - start));
    }


}
