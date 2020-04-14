package com.example.oauth2.controller;

import com.example.oauth2.controller.request.NewCoffeeRequest;
import com.example.oauth2.model.Coffee;
import com.example.oauth2.representation.CoffeeRepresentation;
import com.example.oauth2.representation.CoffeeRepresentationAssembler;
import com.example.oauth2.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/coffee")
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("")
    public  CollectionModel<CoffeeRepresentation> getAllCoffee() {
        List<Coffee> coffees = coffeeService.getAllCoffee();
        CoffeeRepresentationAssembler assembler = new  CoffeeRepresentationAssembler();
        CollectionModel<CoffeeRepresentation> collectionModel = assembler.toCollectionModel(coffees);
        return collectionModel;
    }

    @GetMapping("/")
    public CoffeeRepresentation findCoffeeByName(@RequestParam String name) {
        Coffee coffee = coffeeService.findOneCoffee(name);
        CoffeeRepresentationAssembler assembler = new  CoffeeRepresentationAssembler();
        CoffeeRepresentation representation = assembler.toModel(coffee);
        return representation;
    }

    @GetMapping("/{id}")
    public CoffeeRepresentation findCoffeeById(@PathVariable Long id) {
        Coffee coffee = coffeeService.getCoffeeById(id);
        CoffeeRepresentationAssembler assembler = new  CoffeeRepresentationAssembler();
        CoffeeRepresentation representation = assembler.toModel(coffee);
        return representation;
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('admin')")
    public Coffee saveCoffee(@Valid @RequestBody NewCoffeeRequest coffee) {
        return coffeeService.saveCoffee(coffee);
    }






}
