package com.example.oauth2.service;

import com.example.oauth2.controller.request.NewOrderRequest;
import com.example.oauth2.controller.request.UpdateOrderStateRequest;
import com.example.oauth2.model.Coffee;
import com.example.oauth2.model.CoffeeOrder;
import com.example.oauth2.model.OrderState;
import com.example.oauth2.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
public class CoffeeOrderService {

    @Autowired
    private CoffeeOrderRepository orderRepository;

    public CoffeeOrder createOrder(String customer, Coffee...coffee) {
        CoffeeOrder order = CoffeeOrder.builder()
                .customer(customer)
                .coffees(new ArrayList<>(Arrays.asList(coffee)))
                .state(OrderState.INIT)
                .build();
        CoffeeOrder saved = orderRepository.save(order);
        log.info("New Order: {}", saved);
        return saved;

    }

    public List<CoffeeOrder> findAllOrders() {
        return orderRepository.findAll();
    }

    public CoffeeOrder findOrderById(Long id) {
        return orderRepository.findById(id).get();
    }

    public CoffeeOrder updateOrder(UpdateOrderStateRequest updateOrder) {
        CoffeeOrder order = findOrderById(updateOrder.getId());

        OrderState orderState = OrderState.valueOf(updateOrder.getState());
        if (orderState.compareTo(order.getState()) <= 0) {
            log.warn("Wrong State order: {}, {}", orderState, order.getState());
            return null;
        }
        order.setState(orderState);
        CoffeeOrder updatedOrder = orderRepository.save(order);
        log.info("Updated Order: {}", updatedOrder);
        return updatedOrder;
    }


}