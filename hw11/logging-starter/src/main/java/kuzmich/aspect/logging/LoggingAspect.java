package kuzmich.aspect.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class LoggingAspect {

  private final LoggingProperties properties;

  @Pointcut("@annotation(kuzmich.aspect.logging.Logging)")
  public void loggingMethodsPointcut() {
  }

  @Pointcut("@within(kuzmich.aspect.logging.Logging)")
  public void loggingTypePointcut() {
  }

  @Around(value = "loggingMethodsPointcut() || loggingTypePointcut()")
  public Object loggingMethod(ProceedingJoinPoint pjp) throws Throwable {
    String methodName = pjp.getSignature().getName();
    if(!properties.isPrintArgs()) {
      log.atLevel(properties.getLevel()).log("Before -> ProjectService#{}", methodName);
      try {
        return pjp.proceed();
      } finally {
        log.atLevel(properties.getLevel()).log("After -> ProjectService#{}", methodName);
      }
    } else {
      StringBuilder arguments = getArguments(pjp);
      log.atLevel(properties.getLevel()).log("Before -> Service#{}{}", methodName, arguments);
      try{
        return pjp.proceed();
      } finally {
        log.atLevel(properties.getLevel()).log("After -> Service#{}{}", methodName, arguments);
      }
    }
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
