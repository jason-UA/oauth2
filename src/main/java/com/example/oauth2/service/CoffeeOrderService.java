package com.example.oauth2.service;

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

    public CoffeeOrder updateState(Long id, String state) {
        CoffeeOrder order = orderRepository.findById(id).get();
        OrderState orderState = OrderState.valueOf(state);
        if (orderState.compareTo(order.getState()) <= 0) {
            log.warn("Wrong State order: {}, {}", state, order.getState());
            return null;
        }
        order.setState(orderState);
        CoffeeOrder updateOrder = orderRepository.save(order);
        log.info("Updated Order: {}", updateOrder);
        return updateOrder;
    }


}