package com.mtn.cache;

import com.mtn.config.HybridAuthenticationProvider;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * This acts as a sort of session store, and is isolated into its own class
 * as a service instead of just being a simple map in the AuthenticationProvider
 * for the sole purpose of being able to expand on its functionality later.
 * <p>
 * Created by Allen on 5/10/2017.
 */
@Service
public class AuthenticationCache {

    private Map<String, HybridAuthenticationProvider.MtnAuthentication> authenticationMap = new HashMap<>();

    public HybridAuthenticationProvider.MtnAuthentication get(String key) {
        return authenticationMap.get(key);
    }

    public void put(String accessToken, HybridAuthenticationProvider.MtnAuthentication mtnAuthentication) {
        authenticationMap.put(accessToken, mtnAuthentication);
    }

    public void remove(String accessToken) {
        authenticationMap.remove(accessToken);
    }
}
