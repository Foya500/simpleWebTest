package com.foya.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

import com.foya.dto.UserDTO;
import com.foya.entity.UserEntity;

@WebService(targetNamespace = "http://webservice.test.com/" ,name = "UserWebservice")
public interface UserService {
	
	 @WebMethod(operationName="getUserByName")
	 List<UserDTO> getUserByName(@WebParam(name = "userName") String name);
	
	 
	 @WebMethod(operationName="getUserById")
	 UserDTO getUserById(@WebParam(name = "userId") String userId);
	
	 @WebMethod(operationName="getAllUser")
	 @XmlElement(name="AllUsers")
	 List<UserDTO> getAllUser();
	 
	 UserEntity getUserEntityById(String userId);
	 
}
