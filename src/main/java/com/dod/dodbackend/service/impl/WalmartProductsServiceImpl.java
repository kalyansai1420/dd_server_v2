package com.dod.dodbackend.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dod.dodbackend.model.Product;
import com.dod.dodbackend.model.WalmartCatalogs;
import com.dod.dodbackend.repo.ProductRepo;
import com.dod.dodbackend.service.WalmartProductsService;
import com.dod.dodbackend.util.Walmart;
import com.dod.dodbackend.util.WalmartProducts;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WalmartProductsServiceImpl implements WalmartProductsService{
	
	static final String USERNAME = "IRA4K3ySvbUh3917115tdrHHtKz6Y6tff1";
	static final String PASSWORD = "mXZ.6AWn6YpUFaEMkCAwBwe.BeRdvPaE";
	
	@Autowired
	WalmartCatalogsServiceImpl walmartCatalogsServiceImpl;
	
    @Autowired
    private ProductRepo productRepo;
	

	@Override
	public void fetchDataAndSaveWalmartproducts() throws IOException {

		String credentials = this.USERNAME + ":" + this.PASSWORD;

		String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
		HttpHeaders headers = new HttpHeaders();

		headers.add("Authorization", "Basic " + base64Credentials);
		headers.add("Accept", "application/json");

		HttpEntity<?> httpEntity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();

		List<WalmartCatalogs> catalogs = this.walmartCatalogsServiceImpl.fetchAll();

		for (WalmartCatalogs wc : catalogs) {
			ResponseEntity<String> response = restTemplate
					.exchange("https://api.impact.com/Mediapartners/IRA4K3ySvbUh3917115tdrHHtKz6Y6tff1/Catalogs/"
							+ wc.getId() + "/Items?=", HttpMethod.GET, httpEntity, String.class);
			String responseBody = response.getBody();
			ObjectMapper objectMapper = new ObjectMapper();
			Walmart walmart = objectMapper.readValue(responseBody, Walmart.class);
//			
			List<WalmartProducts> products = walmart.getProducts();
			
			for(WalmartProducts walmartProduct:products) {
				Product pd=new Product();
				//sku
				
				pd.setSku(walmartProduct.getId());
				pd.setCampaign(walmartProduct.getCampaignName()) ;
				pd.setCategory(walmartProduct.getCategory());
				pd.setDescription(walmartProduct.getDescription());
				pd.setDiscountPercentage(walmartProduct.getDiscountPercentage());
				pd.setImage(walmartProduct.getImageUrl());
				pd.setName(walmartProduct.getName());
				pd.setRegularPrice(walmartProduct.getOriginalPrice());
				pd.setSalePrice(walmartProduct.getCurrentPrice());
				pd.setSubCategory(walmartProduct.getSubCategory());
				pd.setUrl(walmartProduct.getUrl());
				pd.setImages(null);
				productRepo.save(pd);
				
				
				
			}
			
			
//			this.walmartProductsRepo.saveAll(products);
			

//			return products;
		}

}
}
