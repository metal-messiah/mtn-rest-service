package com.mtn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Allen on 5/6/2017.
 */
@Controller
public class RedirectController {

    @RequestMapping({"/groups/**", "/roles/**", "/permissions/**", "/users/**"})
    public String angularRedirect() {
        return "/";
    }
}
