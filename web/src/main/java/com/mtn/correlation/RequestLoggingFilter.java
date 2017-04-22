package com.mtn.correlation;

import com.mtn.util.MtnLogger;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Allen on 4/22/2017.
 */
public class RequestLoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        MtnLogger.logHttpRequest(servletRequest);
        filterChain.doFilter(servletRequest, servletResponse);
        MtnLogger.logHttpResponse(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
