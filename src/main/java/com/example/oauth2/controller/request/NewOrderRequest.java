package com.example.oauth2.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@ToString
public class NewOrderRequest {

    @NotEmpty
    private String customer;

    @NotEmpty
    private List<String> Coffees;
}
