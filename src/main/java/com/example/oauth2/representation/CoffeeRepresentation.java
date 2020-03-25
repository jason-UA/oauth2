package com.example.oauth2.representation;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.joda.money.Money;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Data
@Relation(collectionRelation = "coffees")
public class CoffeeRepresentation extends RepresentationModel<CoffeeRepresentation> {

    private Long id;

    private String name;

    private Money price;

    private Date createTime;

    private Date updateTime;
}
