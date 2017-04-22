package com.mtn.correlation;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * This filter is meant to add a correlation ID to incoming requests that can be used
 * in logging, joining log statements together for a single given request.
 * <p>
 * Created by Allen on 4/22/2017.
 */

public class CorrelationIdFilter implements Filter {

    public static final String MTN_CORRELATION_ID_HEADER = "mtn-correlation-id";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //Wrap request in our custom headers enabled wrapper
        CustomHeadersEnabledServletRequest enhancedRequest = new CustomHeadersEnabledServletRequest((HttpServletRequest) request);

        String correlationId = UUID.randomUUID().toString();

        //Add the correlation ID if not already on the request from the client
        if (StringUtils.isBlank(enhancedRequest.getHeader(MTN_CORRELATION_ID_HEADER))) {
            enhancedRequest.addHeader(MTN_CORRELATION_ID_HEADER, correlationId);
        }

        chain.doFilter(enhancedRequest, response);

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader(MTN_CORRELATION_ID_HEADER, correlationId);
    }

    @Override
    public void destroy() {

    }

}
