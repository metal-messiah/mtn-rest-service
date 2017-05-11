package com.mtn.controller;

import com.mtn.model.MtnUserDetails;
import com.mtn.model.view.auth.MtnUserDetailsView;
import com.mtn.model.view.auth.SimpleAccessTokenView;
import com.mtn.service.Auth0Client;
import com.mtn.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Allen on 5/11/2017.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private Auth0Client auth0Client;

    @Autowired
    private SecurityService securityService;

    /**
     * Returns an API Access Token for the given clientId.
     * <p>
     * Note that the header is marked as not required, but it indeed is. We just want the Auth0Client to be able
     * to handle it.
     */
    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public ResponseEntity getApiAccessToken(@RequestHeader(value = "mtn-client-id", required = false) String clientId) {
        String apiAccessToken = auth0Client.getApiAccessToken(clientId);
        return ResponseEntity.ok(new SimpleAccessTokenView(apiAccessToken));
    }

    /**
     * Returns the current user's profile
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity getUserProfile() {
        MtnUserDetails mtnUserDetails = securityService.getCurrentUser();
        if (mtnUserDetails != null) {
            return ResponseEntity.ok(new MtnUserDetailsView(mtnUserDetails));
        } else {
            throw new SecurityException();
        }
    }
}
