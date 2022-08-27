package com.epam.microserviceslearning.common.logging.trace;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.microserviceslearning.common.logging.trace.TraceUtils.TRACE_ID_HEADER;


public class WebMvcTraceInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String traceId = request.getHeader(TRACE_ID_HEADER);

        if (traceId != null) {
            TraceUtils.setTraceId(traceId);
        } else {
            TraceUtils.generateAndSetTraceId();
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        final String traceId = TraceUtils.getTraceId();
        response.setHeader(TRACE_ID_HEADER, traceId);
    }
}
