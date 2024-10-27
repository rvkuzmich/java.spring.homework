package kuzmich.aspect.recover;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class RecoverAspect {

    RecoverProperties recoverProperties;

    public RecoverAspect(RecoverProperties recoverProperties) {
        this.recoverProperties = recoverProperties;
    }

    @Around("@annotation(recover)")
    public Object handleRecover(ProceedingJoinPoint pjp, Recover recover) throws Throwable {
        if (!recoverProperties.isEnabled()) {
            return pjp.proceed();
        }

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Recover recoverAnnotation = method.getAnnotation(Recover.class);
        Class<?>[] noRecoverForAnnotation = recoverAnnotation.noRecoverFor();

        try {
            return pjp.proceed();
        } catch (Throwable e) {
            List<String> noRecoverForConfiguration = recoverProperties.getNoRecoverFor();
            System.out.println(e.getClass().getName());
            boolean noRecover = noRecoverForConfiguration.stream().anyMatch(className -> className.equals(e.getClass().getName()));
            System.out.println(noRecover);

            for (Class<?> exceptionClass : noRecoverForAnnotation) {
                if (exceptionClass.isAssignableFrom(e.getClass()) || noRecover) {
                    throw new RuntimeException(e);
                }
            }

            log.error("Recovering {}#{} after Exception[{}: \"{}\"]",
                    pjp.getSignature().getDeclaringTypeName(),
                    pjp.getSignature().getName(),
                    e.getClass().getSimpleName(),
                    e.getMessage());

            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
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
