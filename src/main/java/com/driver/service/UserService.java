package com.driver.service;

import java.util.List;

import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.service.impl.AlreadyExistsException;
import com.driver.shared.dto.Order;

/**
 * Handle exception cases for all methods which throw Exception
 */
public interface UserService{

	Order createUser(UserDetailsRequestModel user) throws Exception, AlreadyExistsException;
	Order getUser(String email) throws Exception;
	Order getUserByUserId(String userId) throws Exception;
	Order updateUser(String userId, UserDetailsRequestModel user) throws Exception;
	OperationStatusModel deleteUser(String userId) throws Exception;
	List<Order> getUsers();
}
