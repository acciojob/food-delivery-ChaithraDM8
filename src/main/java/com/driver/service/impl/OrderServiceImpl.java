package com.driver.service.impl;

import com.driver.io.Converter.OrderConverter;
import com.driver.io.Converter.RandomStringGenerator;
import com.driver.io.entity.OrderEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    RandomStringGenerator stringGenerator;

    @Override
    public OrderDto createOrder(OrderDto order) throws Exception {
       OrderEntity orderPresent = orderRepository.findByOrderId(order.getOrderId());
        if (orderPresent != null) {
            throw new Exception("Order already exists...!!");
        }
        OrderEntity orderEntity = OrderConverter.convertDtoToEntity(order);
        orderEntity.setOrderId(stringGenerator.generateOrderId(30));
        orderEntity=orderRepository.save(orderEntity);

        return OrderConverter.convertEntityToDto(orderEntity);
    }

    @Override
    public OrderDto getOrderById(String orderId) throws Exception {
       OrderEntity orderEntity=orderRepository.findByOrderId(orderId);
        return OrderConverter.convertEntityToDto(orderEntity);
    }

    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception {
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
    public void deleteOrder(String orderId) throws Exception {
        OrderEntity order=orderRepository.findByOrderId(orderId);
       if(order==null)
           throw new Exception ("order not found!....");
            long id=order.getId();
            orderRepository.deleteById(id);

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