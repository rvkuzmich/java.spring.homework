package kuzmich.hw8.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

  @Before(value = "execution(* kuzmich.hw8.services.TimesheetService.*(..))")
  public void beforeTimesheetServiceFindById(JoinPoint jp) {
    String methodName = jp.getSignature().getName();
//    Long id = (Long) jp.getArgs()[0];
    log.info("Before -> TimesheetService#{}", methodName);
  }
  
}
