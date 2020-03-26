package com.foya.controller;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.foya.bean.Greeting;

@RestController
public class GreetingController {
	  private static final String template = "Hello, %s!";
	    private final AtomicLong counter = new AtomicLong();

	    @RequestMapping("/greeting")
	    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
	    	
	        return new Greeting(counter.incrementAndGet(),
	                            String.format(template, name));
	    }
	    
	    static final String URL_EMPLOYEES = "http://10.1.1.252:8080/BCD_SB/index.jsp";
	    @RequestMapping("/sendReq")
	    public String sendReq(@RequestParam(value="path", defaultValue=URL_EMPLOYEES) String path) {
	    	  RestTemplate restTemplate = new RestTemplate();
	    	  
	          // Send request with GET method and default Headers.
	          String result = restTemplate.getForObject(path, String.class);
	          
	          System.out.println(result);
	          
	          return result;
	    }
	    
	   
}
