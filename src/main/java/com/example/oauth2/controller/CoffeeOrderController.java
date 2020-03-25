package com.example.oauth2.controller;

import com.example.oauth2.controller.request.NewOrderRequest;
import com.example.oauth2.controller.request.UpdateOrderStateRequest;
import com.example.oauth2.model.Coffee;
import com.example.oauth2.model.CoffeeOrder;
import com.example.oauth2.model.OrderState;
import com.example.oauth2.representation.CoffeeOrderRepresentation;
import com.example.oauth2.representation.CoffeeOrderRepresentationAssembler;
import com.example.oauth2.representation.CoffeeRepresentation;
import com.example.oauth2.service.CoffeeOrderService;
import com.example.oauth2.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/order")
public class CoffeeOrderController {

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @Autowired
    private CoffeeService coffeeService;

    private CoffeeOrderRepresentationAssembler assembler = new CoffeeOrderRepresentationAssembler();

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrderRepresentation create(@RequestBody NewOrderRequest newOrderRequest) {
        Coffee[] coffees = coffeeService.getCoffeeByName(newOrderRequest.getCoffees()).toArray(new  Coffee[] {});
        CoffeeOrder coffeeOrder = coffeeOrderService.createOrder(newOrderRequest.getCustomer(), coffees);
        CoffeeOrderRepresentation representation = assembler.toModel(coffeeOrder);
        return representation;
    }

    @GetMapping("")
    public CollectionModel<CoffeeOrderRepresentation> findOrders() {
        List<CoffeeOrder> orderList = coffeeOrderService.findAllOrders();
        CollectionModel<CoffeeOrderRepresentation> collectionModel = assembler.toCollectionModel(orderList);
        return collectionModel;
    }

    @GetMapping("/{id}")
    public CoffeeOrderRepresentation findOrder(@PathVariable Long id) {
        CoffeeOrder coffeeOrder = coffeeOrderService.findOrderById(id);
        CoffeeOrderRepresentation representation = assembler.toModel(coffeeOrder);
        return representation;
    }

    @PutMapping("")
    public CoffeeOrderRepresentation updateCoffee(@RequestBody UpdateOrderStateRequest order) {
        CoffeeOrder coffeeOrder = coffeeOrderService.updateOrder(order);
        CoffeeOrderRepresentation representation = assembler.toModel(coffeeOrder);
        log.info("Updated Order: {}", coffeeOrder);
        log.info("representation Order: {}", representation);
        return representation;
    }
}
