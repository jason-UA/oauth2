package com.example.oauth2.repository;

import com.example.oauth2.controller.request.NewCoffeeRequest;
import com.example.oauth2.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

    List<Coffee> findByNameInOrderById(List<String> list);



}