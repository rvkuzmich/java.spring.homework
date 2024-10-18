package kuzmich.hw8.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RecoverAspect {

    @Around("@annotation(recover)")
    public Object handleRecover(ProceedingJoinPoint joinPoint, Recover recover) {
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            for (Class<?> noRecoverFor : recover.noRecoverFor()) {
                System.out.println(noRecoverFor);
                if (noRecoverFor.isAssignableFrom(e.getClass())) {
                    throw new RuntimeException(e);

                }
            }

            log.error("Recovering {}#{} after Exception[{}: \"{}\"]",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    e.getClass().getSimpleName(),
                    e.getMessage());

            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Class<?> returnType = methodSignature.getReturnType();
            return getDefaultValue(returnType);
        }
    }

    private Object getDefaultValue(Class<?> returnType) {
        if (returnType.isPrimitive()) {
            if (returnType == boolean.class) return false;
            if (returnType == char.class) return '\0';
            if (returnType == byte.class || returnType == short.class || returnType == int.class) return 0;
            if (returnType == long.class) return 0L;
            if (returnType == float.class) return 0.0f;
            if (returnType == double.class) return 0.0;
        }
        return null;
    }
}
