package com.dod.dodbackend.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dod.dodbackend.model.Product;
import com.dod.dodbackend.model.ProductImage;
import com.dod.dodbackend.repo.ProductCustomRepo;
import com.dod.dodbackend.repo.ProductRepo;
import com.dod.dodbackend.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;
    
    @Autowired
    ProductCustomRepo productCustomRepo;

    @Override
    public void fetchDataAndSave() throws JsonMappingException, JsonProcessingException {

        String bestBuyApiUrl = "https://api.bestbuy.com/v1/products(onSale=true&(categoryPath.id=abcat0502000))"
                + "?apiKey=2ZNAIGLk2odeefyi3cBhGFxZ&format=json";

        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.add("Accept", "application/json");

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                bestBuyApiUrl, HttpMethod.GET, httpEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            // Convert the JSON response to a JsonNode
            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            JsonNode productsNode = jsonNode.path("products");

            List<Product> productsToSave = new ArrayList<>();
            for (JsonNode productNode : productsNode) {
                Product product = new Product();
                product.setSku(productNode.path("sku").asText());
                product.setName(productNode.path("name").asText());
                JsonNode descriptionNode = productNode.path("description");
                if (descriptionNode.isMissingNode() || descriptionNode.isNull()) {
                    product.setDescription(productNode.path("longDescription").asText());
                } else {
                    product.setDescription(descriptionNode.asText());
                }
                JsonNode categoryPathNode = productNode.path("categoryPath");
                if (categoryPathNode.isArray() && categoryPathNode.size() >= 2) {
                    // Set the first and second categories
                    product.setCategory(categoryPathNode.path(0).path("name").asText());
                    product.setSubCategory(categoryPathNode.path(1).path("name").asText());
                } else {
                    // Handle the case where categoryPath is not as expected
                    // You may set default values or log a warning/error
                }
                product.setCampaign(productNode.path("manufacturer").asText());
                product.setRegularPrice(productNode.path("regularPrice").asText());
                product.setSalePrice(productNode.path("salePrice").asText());
                product.setUrl(productNode.path("url").asText());
                product.setImage(productNode.path("image").asText());
                JsonNode imagesNode = productNode.path("images");
                List<ProductImage> productImages = new ArrayList<>();
                for (JsonNode imageNode : imagesNode) {
                    ProductImage productImage = new ProductImage();
                    productImage.setImageUrl(imageNode.path("href").asText());
                    productImage.setProduct(product);

                    productImages.add(productImage);
                }

                product.setImages(productImages);

                productsToSave.add(product);
            }

            this.productRepo.saveAll(productsToSave);
        } else {
            System.err.println("Error fetching data from the Best Buy API. Status: " + response.getStatusCode());
        }

    }

	@Override
	public List<Product> search(String s) {
		List<Product> list;
		if(s.contains(" ")) {
			list=this.productRepo.indexSearch(s);
		}
		else {
			list=this.productRepo.searchByName(s);
		}
		return list;
	}
	
	public Integer createIndex() {
		if (this.productRepo.showFullTextindex() == 0) {
//			this.walmartProductsRepo.createFullTextIndex();
			this.productCustomRepo.createIFullTextndex();
		}
		return this.productRepo.showFullTextindex();
	}

	@Override
	public String deleteProducts() {
		this.productRepo.deleteAll();
		return "Successfully deleted";
		
	}

    @Override
    public List<Product> getLaptopDeals() {
        return this.productRepo.getLaptops();
      }

    @Override
    public List<Product> getGamingAndMonitorDeals() {
        return this.productRepo.getGamingAndMonitor();
    }

    @Override
    public List<Product> getTopDeals() {
        return this.productRepo.getTopDeals();
    }

    @Override
    public List<Product> getElectronicsDeals() {
        return this.productRepo.getElectronics();
    }

    
}
