package com.driver.service.impl;

import com.driver.io.Converter.OrderConverter;
import com.driver.io.Converter.UserConverter;
import com.driver.io.entity.OrderEntity;
import com.driver.io.entity.UserEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import com.driver.shared.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDetailsRequestModel order) throws AlreadyExistsException {
       OrderEntity orderPresent = orderRepository.findByOrderId(order.getOrderId());
        if (orderPresent != null) {
            throw new AlreadyExistsException("Order already exists...!!");
        }
        OrderEntity orderEntity = OrderConverter.ConvertRequestToEntity(order);
        orderEntity=orderRepository.save(orderEntity);

        return OrderConverter.convertEntityToDto(orderEntity);
    }

    @Override
    public OrderDto getOrderById(String orderId) throws Exception {
       OrderEntity orderEntity=orderRepository.findByOrderId(orderId);
        return OrderConverter.convertEntityToDto(orderEntity);
    }

    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDetailsRequestModel order) throws Exception {
        long id= orderRepository.findByOrderId(orderId).getId();
        OrderEntity orderEntity= OrderEntity.builder()
                .id(id)
                .userId(order.getUserId())
                .status(true)
                .cost(order.getCost())
                .items(order.getItems())
                .orderId(orderId)
                .build();
      orderEntity=orderRepository.save(orderEntity);
      return OrderConverter.convertEntityToDto(orderEntity);
    }

    @Override
    public OperationStatusModel deleteOrder(String orderId) throws Exception {
        OrderEntity order=orderRepository.findByOrderId(orderId);
        OperationStatusModel operationStatusModel;
        if(order==null)
        {

            operationStatusModel= OperationStatusModel.builder()
                    .operationResult(RequestOperationStatus.ERROR.toString())
                    .operationName(RequestOperationName.DELETE.toString())
                    .build();
        }
        else{
            long id=order.getId();
            orderRepository.deleteById(id);
            operationStatusModel= OperationStatusModel.builder()
                    .operationResult(RequestOperationStatus.SUCCESS.toString())
                    .operationName(RequestOperationName.DELETE.toString())
                    .build();
        }
        return  operationStatusModel;
    }

    @Override
    public List<OrderDto> getOrders() {
        List<OrderDto> orderList = new ArrayList<>();
        List<OrderEntity> orderlist;
        orderlist = orderRepository.findAll();

        for (OrderEntity order : orderlist) {
            OrderDto orderDto = OrderConverter.convertEntityToDto(order);
            orderList.add(orderDto);
        }
        return orderList;
    }
}