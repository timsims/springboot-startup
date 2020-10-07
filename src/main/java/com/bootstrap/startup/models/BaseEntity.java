package com.bootstrap.startup.models;

import org.modelmapper.ModelMapper;

public class BaseEntity {

    public <D> D convertTo(Class<D> destinationType) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, destinationType);
    }
}
