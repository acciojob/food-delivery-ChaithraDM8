package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    FoodRepository foodRepository;

    @Override
    public FoodDetailsResponse createFood(FoodDetailsRequestModel food) throws AlreadyExistsException {
        FoodEntity foodNamePresent = foodRepository.findByFoodId(food.getFoodId());
        if (foodNamePresent != null) {
            throw new AlreadyExistsException("Food already exists...!!");
        }
        FoodEntity foodEntity= convertRequestToEntity(food);
        foodEntity=foodRepository.save(foodEntity);

        // converting foodentity to fooddetailsresponse
        FoodDetailsResponse foodDetailsResponse= convertEntityToResponse(foodEntity);
        return foodDetailsResponse;
    }

    @Override
    public FoodDetailsResponse getFoodById(String foodId) throws Exception {
        FoodEntity food=foodRepository.findByFoodId(foodId);
       FoodDetailsResponse foodDetailsResponse= convertEntityToResponse(food);
       return foodDetailsResponse;
    }

    @Override
    public FoodDetailsResponse updateFoodDetails(String foodId, FoodDetailsRequestModel foodDetails) throws Exception {
        long id= foodRepository.findByFoodId(foodId).getId();
        FoodEntity food= FoodEntity.builder()
                .id(id)
                .foodName(foodDetails.getFoodName())
                .foodCategory(foodDetails.getFoodCategory())
                .foodPrice(foodDetails.getFoodPrice())
                .foodId(foodDetails.getFoodId())
                .build();
        food=foodRepository.save(food);
       return convertEntityToResponse(food);

    }

    @Override
    public OperationStatusModel deleteFoodItem(String foodId) throws Exception {
        FoodEntity food=foodRepository.findByFoodId(foodId);
        OperationStatusModel operationStatusModel;
        if(food==null)
        {

           operationStatusModel= OperationStatusModel.builder()
               .operationResult(RequestOperationStatus.ERROR.toString())
                    .operationName(RequestOperationName.DELETE.toString())
                    .build();
        }
        else{
            long id=food.getId();
            foodRepository.deleteById(id);
            operationStatusModel= OperationStatusModel.builder()
                    .operationResult(RequestOperationStatus.SUCCESS.toString())
                    .operationName(RequestOperationName.DELETE.toString())
                    .build();
        }
        return  operationStatusModel;

    }

    @Override
    public List<FoodDetailsResponse> getFoods() {
        List<FoodDetailsResponse> foodList=new ArrayList<>();
        List<FoodEntity> foodlist;
        foodlist=foodRepository.findAll();

        for(FoodEntity food:foodlist){
           FoodDetailsResponse foodDetailsResponse=convertEntityToResponse(food);
           foodList.add(foodDetailsResponse);
        }
        return foodList;
    }

    public FoodDetailsResponse convertEntityToResponse(FoodEntity food) {
        FoodDetailsResponse foodDetailsResponse=  FoodDetailsResponse.builder()
                .foodId(food.getFoodId())
                .foodName(food.getFoodName())
                .foodPrice(food.getFoodPrice())
                .foodCategory(food.getFoodCategory())
                .build();
        return foodDetailsResponse;
    }
    public FoodEntity convertRequestToEntity(FoodDetailsRequestModel food){
        FoodEntity foodEntity= FoodEntity.builder()
                .foodName(food.getFoodName())
                .foodCategory(food.getFoodCategory())
                .foodPrice(food.getFoodPrice())
                .foodId(food.getFoodId())
                .build();
        return foodEntity;
    }
}