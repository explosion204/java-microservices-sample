package com.epam.microserviceslearning.common.logging;

import com.epam.microserviceslearning.common.logging.appender.LogAppender;
import com.epam.microserviceslearning.common.logging.model.LogType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.microserviceslearning.common.logging.model.LogType.ERROR;
import static com.epam.microserviceslearning.common.logging.model.LogType.INFO;
import static com.epam.microserviceslearning.common.logging.model.LogType.WARN;

@Service
@RequiredArgsConstructor
public class LoggingService {
    private final List<LogAppender> appenders;

    public void info(String messageFormat, Object ... args) {
        log(INFO, messageFormat, args);
    }

    public void error(String messageFormat, Object ... args) {
        log(ERROR, messageFormat, args);
    }

    public void warn(String messageFormat, Object ... args) {
        log(WARN, messageFormat, args);
    }

    public void log(LogType type, String messageFormat, Object ... args) {
        final String formattedMessage = String.format(messageFormat, args);
        appenders.forEach(appender -> appender.append(type, formattedMessage));
    }
}
