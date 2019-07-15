package com.foya.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.jws.WebService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.UserDao;
import com.foya.dto.UserDTO;
import com.foya.entity.UserEntity;
import com.foya.service.UserService;

@WebService(
        targetNamespace = "http://webservice.test.com/", 
        name = "UserWebservice",                 
        serviceName = "UserService",          
        portName = "UserWebservicePortName",   
        endpointInterface = "com.foya.service.UserService")
public class UserServiceImpl implements UserService {

	 private Logger logger = LoggerFactory.getLogger(this.getClass());

	 @Autowired
	 UserDao userDao;

	 @Autowired
	 private ModelMapper modelMapper;
	@Override
	public List<UserDTO> getUserByName(String name) {
	
		List<UserDTO> collect = userDao.findLikeUserName(name).map(u -> ( modelMapper.map(u, UserDTO.class)) ).collect(Collectors.toList());
		return collect;
	}

	@Override
	public UserDTO getUserById(String userId) {
		return modelMapper.map(userDao.findByUserId(userId), UserDTO.class);
	}

	@Override
	public List<UserDTO> getAllUser() {
		List<UserDTO> collect =new ArrayList<UserDTO>();
		userDao.findAll().forEach(u -> {collect.add(modelMapper.map(u, UserDTO.class));});
		return collect;
	}

	@Override
	public UserEntity getUserEntityById(String userId) {
		
		return userDao.findByUserId(userId);
	}

}
