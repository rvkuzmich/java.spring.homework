package kuzmich.hw8.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

  @Pointcut("execution(* kuzmich.hw8.services.TimesheetService.*(..))")
  public void timesheetServiceMethodsPointcut() {
  }

  @Before(value = "timesheetServiceMethodsPointcut()")
  public void beforeTimesheetServiceFindById(JoinPoint jp) { //Method name should describe point of join (beforeTimesheetServiceFindById or beforeTimesheetServiceMethods for all methods)
    String methodName = jp.getSignature().getName();
//    Long id = (Long) jp.getArgs()[0];
    log.info("Before -> TimesheetService#{}", methodName);
  }

  @After(value = "timesheetServiceMethodsPointcut()")
  public void afterTimesheetServiceMethods(JoinPoint jp) {
    String methodName = jp.getSignature().getName();
//    Long id = (Long) jp.getArgs()[0];
    log.info("After -> TimesheetService#{}", methodName);
  }

  @AfterThrowing(value = "timesheetServiceMethodsPointcut()", throwing = "ex")
  public void afterThrowingTimesheetServiceMethods(JoinPoint jp, Exception ex) {
    String methodName = jp.getSignature().getName();
//    Long id = (Long) jp.getArgs()[0];
    log.info("AfterThrowing -> TimesheetService#{} -> {}", methodName, ex.getClass().getName());
  }

  @Around(value = "timesheetServiceMethodsPointcut()")
  public Object aroundTimesheetServiceMethods(ProceedingJoinPoint pjp) throws Throwable {
    long start = System.currentTimeMillis();
    try{
      return pjp.proceed();
    } finally {
      long duration = System.currentTimeMillis() - start;
      log.info("TimesheetService#{} duration = {}ms", pjp.getSignature().getName(), duration);
    }
    
  }
  
}
