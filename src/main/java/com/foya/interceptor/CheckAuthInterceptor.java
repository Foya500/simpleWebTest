package com.foya.interceptor;


import java.util.Base64;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.SOAPException;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.foya.entity.UserEntity;
import com.foya.service.UserService;


public class CheckAuthInterceptor  extends AbstractPhaseInterceptor<SoapMessage>{
	private final Logger log = LoggerFactory.getLogger(CheckAuthInterceptor.class);
	
	@Autowired
	UserService userService;

    private static final String BASIC_PREFIX = "Basic ";
    private static final String USERNAME = "arda";
    private static final String PASSWORD = "123456";
    
	public CheckAuthInterceptor() {
		super(Phase.PRE_INVOKE);// 拦截节点：调用之前
	}

	@Override
	public void handleFault(SoapMessage message) {
		log.warn("Auth Fault：{}",  message);
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		//authByHTTPHeader(message);
		//authBySoapHeader(message);
	}
	
	public void authBySoapHeader(SoapMessage message) throws Fault {
		log.info("start authBySoapHeader：{}", message);
		// 处理方法
		List<Header> headers = message.getHeaders();

		// 判断是否存header
		
		if (headers == null | headers.size() < 1) {
			throw new Fault(new IllegalArgumentException("Auth Fail，Please input the correct Parameter(code:40001)"));
		}
		//取出header
		Header header = headers.get(0);
		
		Element element = (Element) header.getObject();//
		NodeList tokenNode = element.getChildNodes();
		if(tokenNode == null || tokenNode.getLength() < 1) {
		
			throw new Fault(new IllegalArgumentException("Auth Fail，Please input the correct Parameter(code:40002)"));
		}
		
		
		String userName=null;
		String password=null;
		for (int i = 0; i < tokenNode.getLength(); i++) {
			Node array_element = tokenNode.item(i);
			if(array_element!=null) {
				if(array_element.getNodeName()!=null &&
						array_element.getNodeName().indexOf("UsernameToken")>-1) {
					NodeList tempUserNameToken =array_element.getChildNodes();
					for(int j = 0; j < tempUserNameToken.getLength(); j++) {
						Node second_element = tempUserNameToken.item(j);
						if(second_element.getNodeName()!=null && 
								second_element.getNodeName().indexOf("Username")>-1) {
							userName=second_element.getTextContent();
						}else if(second_element.getNodeName()!=null && 
								second_element.getNodeName().indexOf("Password")>-1) {
							password=second_element.getTextContent();
						}
					}
				}
			}
		}
		
		log.info("userName：{} , password:{} ", userName,password);
		boolean result =validateUserPassword(userName, password);
		if (!result) {
            SOAPException exception = new SOAPException("auth failed, Username or Password is not correct");
            throw new Fault(exception);
        }
		
	}
	
	
	 public void authByHTTPHeader(SoapMessage message) throws Fault {
	        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
	        String auth = request.getHeader("Authorization");
	        if (auth == null) {
	            SOAPException exception = new SOAPException("auth failed, header [Authorization] not exists");
	            throw new Fault(exception);
	        }
	        if (!auth.startsWith(BASIC_PREFIX)) {
	            SOAPException exception = new SOAPException("auth failed, header [Authorization] is illegal");
	            throw new Fault(exception);
	        }
	        String plaintext = new String(Base64.getDecoder().decode(auth.substring(BASIC_PREFIX.length())));
	        if (StringUtils.isEmpty(plaintext) || !plaintext.contains(":")) {
	            SOAPException exception = new SOAPException("auth failed, header [Authorization] is illegal");
	            throw new Fault(exception);
	        }
	        String[] userAndPass = plaintext.split(":");
	        String username = userAndPass[0];
	        String password = userAndPass[1];
	        if (!USERNAME.equals(username) || !PASSWORD.equals(password)) {
	            SOAPException exception = new SOAPException("auth failed, username or password is incorrect");
	            throw new Fault(exception);
	        }
	    }
	
	 
	 private boolean validateUserPassword(String userName,String password) {
		 boolean result =false;
		 UserEntity dto=userService.getUserEntityById(userName);
		 if(dto!=null) {
			 result=password.equals(dto.getPassword());
		 }
		 return result;
	 }
	
}
