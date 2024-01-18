package com.dod.dodbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ProductService {
    

    public void fetchDataAndSave() throws JsonMappingException, JsonProcessingException;

}
