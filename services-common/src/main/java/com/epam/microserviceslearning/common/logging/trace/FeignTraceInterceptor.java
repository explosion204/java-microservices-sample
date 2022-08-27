package com.epam.microserviceslearning.common.logging.trace;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import static com.epam.microserviceslearning.common.logging.trace.TraceUtils.TRACE_ID_HEADER;

public class FeignTraceInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        final String traceId = TraceUtils.getTraceId();
        template.header(TRACE_ID_HEADER, traceId);
    }
}
