package com.mtn.security;

import com.auth0.jwk.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.spring.security.api.JwtAuthenticationProvider;
import com.auth0.spring.security.api.authentication.JwtAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.security.interfaces.RSAPublicKey;

public class CustomJwtAuthenticationProvider implements AuthenticationProvider {
    private static Logger logger = LoggerFactory.getLogger(JwtAuthenticationProvider.class);
    private final byte[] secret;
    private final String issuer;
    private final String audience;
    private final JwkProvider jwkProvider;

    public CustomJwtAuthenticationProvider(byte[] secret, String issuer, String audience) {
        this.secret = secret;
        this.issuer = issuer;
        this.audience = audience;
        this.jwkProvider = null;
    }

    public CustomJwtAuthenticationProvider(JwkProvider jwkProvider, String issuer, String audience) {
        this.jwkProvider = jwkProvider;
        this.secret = null;
        this.issuer = issuer;
        this.audience = audience;
    }

    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.isAssignableFrom(authentication);
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!this.supports(authentication.getClass())) {
            return null;
        } else {
            JwtAuthentication jwt = (JwtAuthentication) authentication;

            try {
                JWTVerifier verifier = this.jwtVerifier(jwt);
                Authentication jwtAuth = jwt.verify(verifier);
                logger.info("Authenticated with jwt with scopes {}", jwtAuth.getAuthorities());
                return jwtAuth;
            } catch (JWTVerificationException var4) {
                throw new BadCredentialsException("Not a valid token", var4);
            }
        }
    }

    private JWTVerifier jwtVerifier(JwtAuthentication authentication) throws AuthenticationException {
        if (this.secret != null) {
            return providerForHS256(this.secret, this.issuer, this.audience);
        } else {
            String kid = authentication.getKeyId();
            if (kid == null) {
                throw new BadCredentialsException("No kid found in jwt");
            } else if (this.jwkProvider == null) {
                throw new AuthenticationServiceException("Missing jwk provider");
            } else {
                try {
                    Jwk jwk = this.jwkProvider.get(kid);
                    return providerForRS256((RSAPublicKey) jwk.getPublicKey(), this.issuer, this.audience);
                } catch (SigningKeyNotFoundException var4) {
                    throw new AuthenticationServiceException("Could not retrieve jwks from issuer", var4);
                } catch (InvalidPublicKeyException var5) {
                    throw new AuthenticationServiceException("Could not retrieve public key from issuer", var5);
                } catch (JwkException var6) {
                    throw new AuthenticationServiceException("Cannot authenticate with jwt", var6);
                }
            }
        }
    }

    private static JWTVerifier providerForRS256(RSAPublicKey key, String issuer, String audience) {
        return JWT.require(Algorithm.RSA256(key)).acceptLeeway(60 * 30).withIssuer(issuer).withAudience(new String[]{audience}).build();
    }

    private static JWTVerifier providerForHS256(byte[] secret, String issuer, String audience) {
        return JWT.require(Algorithm.HMAC256(secret)).acceptLeeway(60 * 30).withIssuer(issuer).withAudience(new String[]{audience}).build();
    }
}
