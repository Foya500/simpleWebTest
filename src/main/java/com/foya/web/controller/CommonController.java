package com.foya.web.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.foya.web.bean.Greeting;

@Controller
public class CommonController {
	  
	    @GetMapping("/index2")
	    public String index(@RequestParam(value="name", defaultValue="World") String name) {
	    	
	       return "index2";
	    }
}
