package com.epam.microserviceslearning.common.logging.appender;

import com.epam.microserviceslearning.common.logging.model.LogType;

public interface LogAppender {
    void append(LogType type, String message);
}
