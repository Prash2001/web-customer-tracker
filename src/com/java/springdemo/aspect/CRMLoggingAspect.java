package com.java.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {
  // setup logger
  private Logger myLogger = Logger.getLogger(getClass().getName());

  // setup pointcut declaration
  @Pointcut("execution(* com.java.springdemo.controller.*.*(..))")
  private void forControllerPackage() {

  }

  @Pointcut("execution(* com.java.springdemo.service.*.*(..))")
  private void forServicePackage() {

  }

  @Pointcut("execution(* com.java.springdemo.dao.*.*(..))")
  private void forDAOPackage() {

  }

  @Pointcut("forControllerPackage()||forServicePackage()||forDAOPackage()")
  private void forAppFlow() {

  }

  @Before("forAppFlow()")
  private void before(JoinPoint theJoinPoint) {
    String theMethod = theJoinPoint.getSignature().toShortString();
    myLogger.info("in @Before Calling : " + theMethod);
    Object[] args = theJoinPoint.getArgs();
    for(Object arg : args) {
      myLogger.info(arg);
    }
  }
  
  @AfterReturning(
      pointcut = "forAppFlow()",
      returning = "result")
  private void before(JoinPoint theJoinPoint, Object result) {
    String theMethod = theJoinPoint.getSignature().toShortString();
    myLogger.info("in @AfterReturning Calling : " + theMethod);
    myLogger.info("Result --> "+result);
  }
}
