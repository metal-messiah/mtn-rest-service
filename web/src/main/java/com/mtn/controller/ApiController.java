package com.mtn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by Allen on 4/20/2017.
 */
@Controller
@RequestMapping( "/api" )
public class ApiController {
	
	@ApiIgnore
	@RequestMapping( method = RequestMethod.GET )
	public String redirectToSwaggerDocs() {
		return "redirect:swagger-ui.html";
	}
}