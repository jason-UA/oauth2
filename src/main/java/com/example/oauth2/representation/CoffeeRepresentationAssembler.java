package com.example.oauth2.representation;

import com.example.oauth2.controller.CoffeeController;
import com.example.oauth2.model.Coffee;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class CoffeeRepresentationAssembler extends RepresentationModelAssemblerSupport<Coffee, CoffeeRepresentation> {


    public CoffeeRepresentationAssembler() {
        super(CoffeeController.class, CoffeeRepresentation.class);
    }


    @Override
    public CoffeeRepresentation toModel(Coffee entity) {
        CoffeeRepresentation representation = createModelWithId(entity.getId(), entity);
        return representation;
    }

    @Override
    protected CoffeeRepresentation createModelWithId(Object id, Coffee entity) {
        CoffeeRepresentation representation = super.createModelWithId(id, entity);
        representation.setName(entity.getName());
        representation.setPrice(entity.getPrice());
        representation.setId(entity.getId());
        representation.setCreateTime(entity.getCreateTime());
        representation.setUpdateTime(entity.getUpdateTime());
        return representation;
    }
}
