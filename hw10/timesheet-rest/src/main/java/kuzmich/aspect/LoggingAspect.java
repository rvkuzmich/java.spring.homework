package kuzmich.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
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
    StringBuilder arguments = getArguments(jp);
    log.info("Before -> TimesheetService#{}{}", methodName, arguments);
  }

  @After(value = "timesheetServiceMethodsPointcut()")
  public void afterTimesheetServiceMethods(JoinPoint jp) {
    String methodName = jp.getSignature().getName();
    StringBuilder arguments = getArguments(jp);
    log.info("After -> TimesheetService#{}{}", methodName, arguments);
  }

  @AfterThrowing(value = "timesheetServiceMethodsPointcut()", throwing = "ex")
  public void afterThrowingTimesheetServiceMethods(JoinPoint jp, Exception ex) {
    String methodName = jp.getSignature().getName();
    StringBuilder arguments = getArguments(jp);
    log.info("AfterThrowing -> TimesheetService#{} {} -> {}", methodName, arguments, ex.getClass().getName());
  }

  private StringBuilder getArguments(JoinPoint jp) {
    Object[] args = jp.getArgs();
    StringBuilder sb = new StringBuilder("(");
    for (int i = 0; i < args.length; i++) {
      if(args[i] == null) {
        return sb.append(")");
      }
      if (i > 0) sb.append(", ");
      sb.append(args[i].getClass().getSimpleName()).append(" = ").append(args[i]);
    }
    sb.append(")");
    return sb;
  }
}
