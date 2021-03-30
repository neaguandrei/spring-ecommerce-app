package com.fmi.common.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    private long start;

    @Before("@annotation(com.fmi.common.logging.EnteringExitingLog)")
    public void enteringMethod(JoinPoint joinPoint) {
        log.info("Entering method {} in {}", joinPoint.getSignature().getName(),
                joinPoint.getSourceLocation().getWithinType().getName());
        start = System.currentTimeMillis();
    }


    @After("@annotation(com.fmi.common.logging.EnteringExitingLog)")
    public void exitingMethod(JoinPoint joinPoint) {
        long end = System.currentTimeMillis();
        log.info("Exiting method {} in {} with duration {} milliseconds.",
                joinPoint.getSignature().getName(), joinPoint.getSourceLocation().getWithinType().getName(), (end - start));
    }


}
