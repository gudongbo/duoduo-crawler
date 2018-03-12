package com.duoduo.crawler.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cl")
public class BaseController {
	@RequestMapping(value = "")
	public String welcome() {
		return "welcome";
	}
}
