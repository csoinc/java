package com.cso.spring3.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/canvas")
public class CanvasController {
	protected final Log logger = LogFactory.getLog(getClass());


	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		
		model.addAttribute("message", "Canvas");
		return "canvas";
		
	}
	

}