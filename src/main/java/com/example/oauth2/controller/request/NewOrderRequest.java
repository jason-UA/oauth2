package com.example.oauth2.controller.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ToString
public class NewOrderRequest {

    @NotEmpty
    private String customer;

    @NotEmpty
    private List<String> coffees;


}
