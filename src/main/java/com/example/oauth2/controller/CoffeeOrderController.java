package com.example.oauth2.controller;

import com.example.oauth2.controller.request.NewOrderRequest;
import com.example.oauth2.model.Coffee;
import com.example.oauth2.model.CoffeeOrder;
import com.example.oauth2.model.OrderState;
import com.example.oauth2.service.CoffeeOrderService;
import com.example.oauth2.service.CoffeeService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoffeeOrderController {

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @Autowired
    private CoffeeService coffeeService;

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder create(@RequestBody NewOrderRequest newOrderRequest) {
        Coffee[] coffees = coffeeService.getCoffeeByName(newOrderRequest.getCoffees()).toArray(new  Coffee[] {});
        return coffeeOrderService.createOrder(newOrderRequest.getCustomer(), coffees);
    }

    @GetMapping("/orders")
    public List<CoffeeOrder> findOrders() {
        return coffeeOrderService.findAllOrders();
    }

    @PutMapping("/order")
    public CoffeeOrder updateCoffeeState(@RequestParam Long id, @RequestParam String state) {
        return coffeeOrderService.updateState(id, state);
    }
}
