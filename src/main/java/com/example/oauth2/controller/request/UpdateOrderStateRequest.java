package com.example.oauth2.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
public class UpdateOrderStateRequest {

    private Long id;

    private String state;
}
