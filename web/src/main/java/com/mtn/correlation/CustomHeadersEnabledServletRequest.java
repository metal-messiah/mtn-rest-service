package com.mtn.correlation;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * This special wrapper for HttpServletRequests allows us to add custom headers to the request at runtime.
 * <p>
 * Created by Allen on 4/22/2017.
 */
public class CustomHeadersEnabledServletRequest extends HttpServletRequestWrapper {
    private final Map<String, String> customHeaders = new HashMap<>();

    public CustomHeadersEnabledServletRequest(HttpServletRequest request) {
        super(request);
    }

    public CustomHeadersEnabledServletRequest addHeader(String key, String value) {
        customHeaders.put(key, value);
        return this;
    }

    @Override
    public String getHeader(String name) {
        //Check custom headers first
        String header = customHeaders.get(name);

        //If header not found, check provided headers
        if (StringUtils.isBlank(header)) {
            header = super.getHeader(name);
        }

        return header;
    }
}
