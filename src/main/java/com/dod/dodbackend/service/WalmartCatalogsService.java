package com.dod.dodbackend.service;

import java.util.List;

import com.dod.dodbackend.model.WalmartCatalogs;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface WalmartCatalogsService {
	public void fetchDataAndSave() throws JsonMappingException, JsonProcessingException;
	
	public List<WalmartCatalogs> fetchAll();
	public int getTotalPages();
	public String deleteCatalogs();
	
}
