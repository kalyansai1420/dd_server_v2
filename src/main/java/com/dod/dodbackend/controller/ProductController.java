package com.dod.dodbackend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dod.dodbackend.model.Product;
import com.dod.dodbackend.service.BestBuyService;
import com.dod.dodbackend.service.DellService;
import com.dod.dodbackend.service.IkeaService;
import com.dod.dodbackend.service.ProductService;
import com.dod.dodbackend.service.WalmartCatalogsService;
import com.dod.dodbackend.service.WalmartProductsService;
import com.dod.dodbackend.util.WalmartProducts;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class ProductController {

	@Autowired
	private BestBuyService bestBuyService;

	@Autowired
	private DellService dellService;

	@Autowired
	private IkeaService ikeaService;
	
	@Autowired
	private WalmartCatalogsService  walmartCatalogsService;
	
	@Autowired
	private WalmartProductsService walmartProductsService;
	
	@Autowired
	private ProductService productService;

    @GetMapping("/bestbuyLaptops")
	public String saveBestBuyLaptops() throws JsonMappingException, JsonProcessingException {
		this.bestBuyService.fetchDataAndSaveBestBuyLaptops();
		this.bestBuyService.fetchDataAndSaveBestBuyLaptops2();
		this.bestBuyService.fetchDataAndSaveBestBuyLaptops3();
		this.bestBuyService.fetchDataAndSaveBestBuyLaptops4();
		this.bestBuyService.fetchDataAndSaveBestBuyLaptops5();
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
	
	@GetMapping("/walmartProducts")
	public String saveWalmartProducts() throws IOException {
		this.walmartProductsService.fetchDataAndSaveWalmartproducts();
		return "saved";
	}
	
	@GetMapping("/search/{search}")
	public ResponseEntity<List<Product>> search(@PathVariable String search){
		return ResponseEntity.ok(this.productService.search(search));		
	}
	
	@GetMapping("/createIndex")
	public ResponseEntity<Integer> createIndex(){
		return ResponseEntity.ok(this.productService.createIndex());		
	}
	
	@DeleteMapping("/deleteProducts")
	public ResponseEntity<String> deleteProducts(){
		return ResponseEntity.ok(this.productService.deleteProducts());
	}


	//Deals on laptops
	@GetMapping("/laptopDeals")
	public ResponseEntity<List<Product>> getLaptops() {
		return ResponseEntity.ok(this.productService.getLaptopDeals());
	}
	
	//Deals on gaming and monitors
	@GetMapping("/gamingAndMonitorDeals")
	public ResponseEntity<List<Product>> getGamingAndMonitorDeals() {
		return ResponseEntity.ok(this.productService.getGamingAndMonitorDeals());
	}

	//Top deals
	@GetMapping("/topDeals")
	public ResponseEntity<List<Product>> getTopDeals() {
		return ResponseEntity.ok(this.productService.getTopDeals());
	}

	//Deals on electronics
	@GetMapping("/electronicsDeals")
	public ResponseEntity<List<Product>> getElectronicsDeals() {
		return ResponseEntity.ok(this.productService.getElectronicsDeals());
	}

	




	
   
}
