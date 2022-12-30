package com.driver.service;

import java.util.List;

import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.service.impl.AlreadyExistsException;
import com.driver.shared.dto.OrderDto;

/**
 * Handle exception cases for all methods which throw Exception
 */
public interface OrderService {

	OrderDto createOrder(OrderDetailsRequestModel order) throws AlreadyExistsException;
	OrderDto getOrderById(String orderId) throws Exception;
	OrderDto updateOrderDetails(String orderId, OrderDetailsRequestModel order) throws Exception;
	OperationStatusModel deleteOrder(String orderId) throws Exception;
	List<OrderDto> getOrders();
}
