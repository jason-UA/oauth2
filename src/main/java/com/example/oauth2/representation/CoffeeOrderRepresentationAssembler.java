package com.example.oauth2.representation;

import com.example.oauth2.controller.CoffeeController;
import com.example.oauth2.controller.CoffeeOrderController;
import com.example.oauth2.model.Coffee;
import com.example.oauth2.model.CoffeeOrder;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class CoffeeOrderRepresentationAssembler extends RepresentationModelAssemblerSupport<CoffeeOrder, CoffeeOrderRepresentation> {

    public CoffeeOrderRepresentationAssembler() {
        super(CoffeeOrderController.class, CoffeeOrderRepresentation.class);
    }

    @Override
    public CoffeeOrderRepresentation toModel(CoffeeOrder entity) {
        CoffeeOrderRepresentation representation = createModelWithId(entity.getId(), entity);
        return representation;
    }

    @Override
    protected CoffeeOrderRepresentation createModelWithId(Object id, CoffeeOrder entity) {
        CoffeeOrderRepresentation representation = super.createModelWithId(id, entity);
        representation.setCoffees(entity.getCoffees());
        representation.setCustomer(entity.getCustomer());
        representation.setState(entity.getState());
        representation.setCreateTime(entity.getCreateTime());
        representation.setUpdateTime(entity.getUpdateTime());
        representation.setId(entity.getId());
        return representation;
    }
}


