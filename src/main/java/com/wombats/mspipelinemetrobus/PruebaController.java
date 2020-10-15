package com.wombats.mspipelinemetrobus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PruebaController {
	
	@GetMapping("/hello")
	public String helloWolrd() {
		return "HELLO WORLD...!";
	}

}
