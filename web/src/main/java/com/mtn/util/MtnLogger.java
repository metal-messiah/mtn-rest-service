package com.mtn.util;

import com.mtn.correlation.CorrelationIdFilter;
import com.mtn.correlation.CustomHeadersEnabledServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class MtnLogger {

    private static final Logger logger = LoggerFactory.getLogger(MtnLogger.class);

    private static final int REQUEST_RESPONSE_PADDING = 8;
    private static final String REQUEST = StringUtils.rightPad("REQUEST", REQUEST_RESPONSE_PADDING);
    private static final String RESPONSE = StringUtils.rightPad("RESPONSE", REQUEST_RESPONSE_PADDING);

    public static void error(String message) {
        logger.error(message);
    }

    public static void error(String message, Object extra) {
        logger.error(message, extra);
    }

    public static void error(String message, Throwable e) {
        logger.error(message, e);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void info(String message, Object extra) {
        logger.info(message, extra);
    }

    public static void info(String message, Throwable e) {
        logger.info(message, e);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void warn(String message, Object extra) {
        logger.warn(message, extra);
    }

    public static void warn(String message, Throwable e) {
        logger.warn(message, e);
    }

    public static void logHttpRequest(ServletRequest servletRequest) {
        CustomHeadersEnabledServletRequest enhancedRequest = (CustomHeadersEnabledServletRequest) servletRequest;

        String correlationId = enhancedRequest.getHeader(CorrelationIdFilter.MTN_CORRELATION_ID_HEADER);
        String message = String.format("%s - %s - %s %s", REQUEST, correlationId, enhancedRequest.getMethod(), enhancedRequest.getRequestURL().toString());

        logger.info(message);
    }

    public static void logHttpResponse(ServletRequest servletRequest, ServletResponse servletResponse) {
        CustomHeadersEnabledServletRequest enhancedRequest = (CustomHeadersEnabledServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String correlationId = enhancedRequest.getHeader(CorrelationIdFilter.MTN_CORRELATION_ID_HEADER);
        int status = httpServletResponse.getStatus();
        String message = String.format("%s - %s - %s %s - %d", RESPONSE, correlationId, enhancedRequest.getMethod(), enhancedRequest.getRequestURL().toString(), status);

        if (status >= 200 && status < 400) {
            logger.info(message);
        } else if (status >= 400 && status < 500) {
            logger.warn(message);
        } else {
            logger.error(message);
        }
    }
}
