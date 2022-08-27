package com.epam.microserviceslearning.common.logging.trace;

import java.util.UUID;

public class TraceUtils {
    public static final String TRACE_ID_HEADER = "TRACE_ID";

    private static final ThreadLocal<String> traceIdHolder = ThreadLocal.withInitial(() -> UUID.randomUUID().toString());

    public static String getTraceId() {
        return traceIdHolder.get();
    }

    public static void setTraceId(String traceId) {
        traceIdHolder.set(traceId);
    }

    public static void generateAndSetTraceId() {
        traceIdHolder.set(UUID.randomUUID().toString());
    }
}
