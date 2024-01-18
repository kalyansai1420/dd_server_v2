package com.dod.dodbackend.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dod.dodbackend.service.BestBuyService;
import com.dod.dodbackend.service.DellService;
import com.dod.dodbackend.service.IkeaService;
import com.dod.dodbackend.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class ProductController {
    
    @Autowired
    private ProductService productService;

	@Autowired
	private BestBuyService bestBuyService;

	@Autowired
	private DellService dellService;

	@Autowired
	private IkeaService ikeaService;

    @GetMapping("/bestbuyLaptops")
	public String saveBestBuyLaptops() throws JsonMappingException, JsonProcessingException {
		this.bestBuyService.fetchDataAndSaveBestBuyLaptops();
		 return "saved";
	}
    @GetMapping("/bestbuyDesktops")
	public String saveBestBuyDesktops() throws JsonMappingException, JsonProcessingException {
		this.bestBuyService.fetchDataAndSaveBestBuyDesktops();
		 return "saved";
	}
    @GetMapping("/bestbuyTVs")
	public String saveBestBuyTV() throws JsonMappingException, JsonProcessingException {
		this.bestBuyService.fetchDataAndSaveBestBuyTVs();
		 return "saved";
	}
    @GetMapping("/bestbuyMobiles")
	public String saveBestBuyGaming() throws JsonMappingException, JsonProcessingException {
		this.bestBuyService.fetchDataAndSaveBestBuyMobiles();
		 return "saved";
	}


	@GetMapping("/dellLaptops")
	public String saveDellLaptops() throws IOException {
		this.dellService.fetchDataAndSaveDellLaptops();
		 return "saved";
	}
	@GetMapping("/dellDesktops")
	public String saveDellDesktops() throws IOException {
		this.dellService.fetchDataAndSaveDellPCs();
		 return "saved";
	}
	@GetMapping("/dellGaming")
	public String saveDellGaming() throws IOException {
		this.dellService.fetchDataAndSaveDellGaming();
		 return "saved";
	}

	@GetMapping("/ikeaSofas")
	public String saveIkeaSofas() throws IOException {
		this.ikeaService.fetchDataAndSaveIkeaSofas();
		 return "saved";
	}

	@GetMapping("/ikeaTables")
	public String saveIkeaTables() throws IOException {
		this.ikeaService.fetchDataAndSaveIkeaTables();
		 return "saved";
	}

	@GetMapping("/ikeaTVCabinets")
	public String saveIkeaTVCabinets() throws IOException {
		this.ikeaService.fetchDataAndSaveIkeaTVCabinets();
		 return "saved";
	}

	@GetMapping("/ikeaChairs")
	public String saveIkeaChairs() throws IOException {
		this.ikeaService.fetchDataAndSaveIkeaChairs();
		 return "saved";
	}
   
}
