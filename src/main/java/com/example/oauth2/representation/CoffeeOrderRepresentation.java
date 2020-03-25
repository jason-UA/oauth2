package com.example.oauth2.representation;

import com.example.oauth2.model.Coffee;
import com.example.oauth2.model.OrderState;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;
import java.util.List;

@Data
@Relation(collectionRelation = "orders")
public class CoffeeOrderRepresentation extends RepresentationModel<CoffeeOrderRepresentation> {

    private Long id;

    private String customer;

    private List<Coffee> coffees;

    private OrderState state;

    private Date createTime;

    private Date updateTime;
}
