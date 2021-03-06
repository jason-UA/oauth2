package com.example.oauth2.service;

import com.example.oauth2.controller.request.NewCoffeeRequest;
import com.example.oauth2.model.Coffee;
import com.example.oauth2.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    public Coffee findOneCoffee(String name) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", new ExampleMatcher.GenericPropertyMatcher().ignoreCase());
        Optional<Coffee> coffee = coffeeRepository.findOne(Example.of(Coffee.builder().name(name).build(), matcher));
        log.info("Coffee Found: {}", coffee);
        return coffee.get();
    }

    public List<Coffee> getCoffeeByName(List<String> names) {
        List<Coffee> coffees =  new ArrayList<>();
        for (String name : names) {
            coffees.add(findOneCoffee(name));
        }
        return coffees;
    }

    public Coffee getCoffeeById(Long id) {
        return coffeeRepository.findById(id).get();
    }


    public List<Coffee> getAllCoffee() {
        return coffeeRepository.findAll(Sort.by("id"));
    }

    public Coffee saveCoffee(NewCoffeeRequest coffee) {
        Coffee newCoffee = Coffee.builder()
                .name(coffee.getName())
                .price(coffee.getPrice())
                .build();
        return coffeeRepository.save(newCoffee);
    }

}
