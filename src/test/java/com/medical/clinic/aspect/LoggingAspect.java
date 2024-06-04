package com.medical.clinic.aspect;

import com.medical.clinic.dto.UserDTOSecurity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("execution(* com.medical.clinic.service..*(..)) || execution(* com.medical.clinic.controller..*(..))")
    public Object logServices(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("Executing {} inside {}", proceedingJoinPoint.getSignature().getName(), proceedingJoinPoint.getThis().getClass().getSimpleName());
        return proceedingJoinPoint.proceed();
    }

    @Around("execution(* com.medical.clinic.controller..*(..))")
    public Object logUser(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDTOSecurity) {
            logger.info("Logged user {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        return proceedingJoinPoint.proceed();
    }
}
