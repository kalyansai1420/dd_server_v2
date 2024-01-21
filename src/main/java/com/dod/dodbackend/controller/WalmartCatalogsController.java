package com.dod.dodbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dod.dodbackend.service.WalmartCatalogsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@CrossOrigin("*")
@RestController
@RequestMapping("/walmart")
public class WalmartCatalogsController {

	@Autowired
	WalmartCatalogsService walmartCatalogsService;

	@GetMapping("/save")
	public String saveAllCatalogs() throws JsonMappingException, JsonProcessingException {
		this.walmartCatalogsService.fetchDataAndSave();
		return "saved";
	}
	
	@DeleteMapping("/deleteAllCatalogs")
	public ResponseEntity<String> deleteAllCatalogs(){
		return ResponseEntity.ok(this.walmartCatalogsService.deleteCatalogs());
	}
}
