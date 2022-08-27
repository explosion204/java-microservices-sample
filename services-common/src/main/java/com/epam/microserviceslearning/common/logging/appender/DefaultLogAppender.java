package com.epam.microserviceslearning.common.logging.appender;

import com.epam.microserviceslearning.common.logging.model.LogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultLogAppender implements LogAppender {
    private static final Logger consoleLogger = LoggerFactory.getLogger(DefaultLogAppender.class);

    @Override
    public void append(LogType type, String message) {
        switch (type) {
            case INFO -> consoleLogger.info(message);
            case WARN -> consoleLogger.warn(message);
            case ERROR -> consoleLogger.error(message);
        }
    }
}
