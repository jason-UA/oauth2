package com.example.oauth2.controller;

import com.example.oauth2.controller.request.NewCoffeeRequest;
import com.example.oauth2.model.Coffee;
import com.example.oauth2.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("/coffees")
    public List<Coffee> getAllCoffee() {
        return coffeeService.getAllCoffee();
    }

    @GetMapping("/coffee")
    public Coffee findCoffeeByName(@RequestParam String name) {
        return coffeeService.findOneCoffee(name);
    }

    @PostMapping("/coffee")
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee saveCoffee(@RequestBody NewCoffeeRequest coffee) {
        return coffeeService.saveCoffee(coffee);
    }






}
