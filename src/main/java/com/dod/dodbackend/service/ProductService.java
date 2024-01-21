package com.dod.dodbackend.service;

import java.util.List;

import com.dod.dodbackend.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ProductService {
    

    public void fetchDataAndSave() throws JsonMappingException, JsonProcessingException;

	public List<Product> search(String s);
	
	//createIndex
	public Integer createIndex();
	
	public String deleteProducts();
	
	

}
