package com.driver.io.Converter;

import com.driver.io.entity.OrderEntity;
import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.shared.dto.OrderDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderConverter {
    public static OrderEntity ConvertRequestToEntity(OrderDetailsRequestModel order) {
        return OrderEntity.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .items(order.getItems())
                .cost(order.getCost())
                .status(false)
               .build();
    }

    public static OrderDto convertEntityToDto(OrderEntity orderEntity) {
        return OrderDto.builder()
                .orderId(orderEntity.getOrderId())
                .cost(orderEntity.getCost())
                .items(orderEntity.getItems())
                .userId(orderEntity.getUserId())
                .status(orderEntity.isStatus())
                .build();
    }

    public static OrderDetailsResponse convertDtoToResponse(OrderDto orderDto) {
        return OrderDetailsResponse.builder()
                .orderId(orderDto.getOrderId())
                .cost(orderDto.getCost())
                .items(orderDto.getItems())
                .userId(orderDto.getUserId())
                .status(orderDto.isStatus())
                .build();
    }
}
