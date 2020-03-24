package com.example.oauth2.controller.request;

import lombok.Data;
import lombok.ToString;
import org.joda.money.Money;

import javax.validation.constraints.NotEmpty;

@Data
@ToString
public class NewCoffeeRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private Long price;
}
