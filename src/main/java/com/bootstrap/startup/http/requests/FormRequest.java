package com.bootstrap.startup.http.requests;

import org.modelmapper.ModelMapper;

public class FormRequest {

    public <D> D toModel(Class<D> destinationType) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, destinationType);
    }
}
