package com.driver.service;

import java.util.List;

import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.service.impl.AlreadyExistsException;

/**
 * Handle exception cases for all methods which throw Exception
 */
public interface FoodService {

	FoodDetailsResponse createFood(FoodDetailsRequestModel food) throws AlreadyExistsException;
	FoodDetailsResponse getFoodById(String foodId) throws Exception;
	FoodDetailsResponse updateFoodDetails(String foodId, FoodDetailsRequestModel foodDetails) throws Exception;
	OperationStatusModel deleteFoodItem(String id) throws Exception;
	List<FoodDetailsResponse> getFoods();
}
