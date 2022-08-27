package com.epam.microserviceslearning.processor.exception;

import com.epam.microserviceslearning.common.logging.LoggingService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ApplicationExceptionHandler {
    private final LoggingService logger;

    @AfterThrowing(pointcut="execution(* com.epam.microserviceslearning.processor..*.*(..))", throwing="e")
    public void handleError(Exception e) {
        logger.error("Caught unexpected exception: %s", e.getMessage());
    }
}
