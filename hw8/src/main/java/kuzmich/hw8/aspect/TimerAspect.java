package kuzmich.hw8.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimerAspect {

  @Pointcut
  public timerPointcut() {
    
  }
  
  @Around(value = "timerPointcut()")
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
