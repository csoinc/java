package com.oyou.spring3.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Controller
@RequestMapping("/helloworld")
public class HelloWorldController {
	protected final Log logger = LogFactory.getLog(getClass());


	@RequestMapping(method = RequestMethod.GET)
	public String helloWorld(ModelMap model) {
		
		model.addAttribute("message", "Spring 3 MVC - Hello World");
		return "helloworld";
		
		
	}
	


}