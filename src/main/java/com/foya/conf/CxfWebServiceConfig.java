package com.foya.conf;

import javax.servlet.Servlet;
import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.cxf.interceptor.Interceptor;

import com.foya.interceptor.CheckAuthInterceptor;
import com.foya.service.UserService;
import com.foya.service.impl.UserServiceImpl;

@Configuration
public class CxfWebServiceConfig {

	@Bean("cxfServletRegistration")
	public ServletRegistrationBean<Servlet> dispatcherServlet() {
		
		return new ServletRegistrationBean<Servlet>(new CXFServlet(), "/ws/*");
	}

	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}

	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
		SpringBus springBus = new SpringBus();
		
		LoggingInInterceptor ipt = new LoggingInInterceptor();
		LoggingOutInterceptor opt = new LoggingOutInterceptor();
		ipt.setPrettyLogging(true);
		//ipt.setLimit(LOG_LIMIT);
		opt.setPrettyLogging(true);
		//opt.setLimit(LOG_LIMIT);
		
		springBus.getInInterceptors().add(ipt);
		springBus.getOutInterceptors().add(opt);
		
		springBus.getInInterceptors().add(createCheckAuthInterceptor());
		
		springBus.getOutFaultInterceptors().add(opt);
		
		return springBus;
	}

	/*
	 * user 類別 endpoint
	 */
	@Bean
	public Endpoint endpoint(UserService userService) {
		EndpointImpl endpoint = new EndpointImpl(springBus(), userService);
		endpoint.publish("/user");// URL
//		endpoint.getInInterceptors().add(createCheckAuthInterceptor());
		return endpoint;
	}

	@Bean
	public Interceptor<SoapMessage> createCheckAuthInterceptor() {
		return new CheckAuthInterceptor();
	}

}
