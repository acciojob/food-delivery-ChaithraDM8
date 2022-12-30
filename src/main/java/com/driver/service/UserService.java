package com.driver.service;

import java.util.List;

import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.service.impl.AlreadyExistsException;
import com.driver.shared.dto.UserDto;

/**
 * Handle exception cases for all methods which throw Exception
 */
public interface UserService{

	UserDto createUser(UserDetailsRequestModel user) throws Exception, AlreadyExistsException;
	UserDto getUser(String email) throws Exception;
	UserDto getUserByUserId(String userId) throws Exception;
	UserDto updateUser(String userId, UserDetailsRequestModel user) throws Exception;
	OperationStatusModel deleteUser(String userId) throws Exception;
	List<UserDto> getUsers();
}
