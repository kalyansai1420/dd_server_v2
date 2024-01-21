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
import com.dod.dodbackend.repo.ProductRepo;
import com.dod.dodbackend.service.BestBuyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BestBuyServiceImpl implements BestBuyService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public void fetchDataAndSaveBestBuyLaptops() throws JsonMappingException, JsonProcessingException {
        String bestBuyLaptopUrl = "https://api.bestbuy.com/v1/products(onSale=true&(categoryPath.id=abcat0502000))?apiKey=2ZNAIGLk2odeefyi3cBhGFxZ&pageSize=100&format=json";
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.add("Accept", "application/json");

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                bestBuyLaptopUrl, HttpMethod.GET, httpEntity, String.class);

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
                if (categoryPathNode.isArray() && categoryPathNode.size() >= 3) {
                    // Set the first and second categories
                    product.setCampaign(categoryPathNode.path(0).path("name").asText());
                    product.setSubCategory(categoryPathNode.path(2).path("name").asText());
                } else {
                    // Handle the case where categoryPath is not as expected
                    // You may set default values or log a warning/error
                }
                product.setCategory("Electronics");
                product.setRegularPrice(productNode.path("regularPrice").asText());
                product.setSalePrice(productNode.path("salePrice").asText());
                product.setDiscountPercentage(getDiscount(productNode.path("regularPrice").asText(), productNode.path("salePrice").asText()));
                
                product.setUrl(productNode.path("url").asText());
                product.setImage(productNode.path("image").asText());
                product.setRating(productNode.path("customerReviewAverage").asText());
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
    public void fetchDataAndSaveBestBuyLaptops2() throws JsonMappingException, JsonProcessingException {
        String bestBuyLaptopUrl = "https://api.bestbuy.com/v1/products(onSale=true&preowned=false&(categoryPath.id=abcat0502000))?apiKey=2ZNAIGLk2odeefyi3cBhGFxZ&pageSize=100&page=2&format=json";
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.add("Accept", "application/json");

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                bestBuyLaptopUrl, HttpMethod.GET, httpEntity, String.class);

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
                if (categoryPathNode.isArray() && categoryPathNode.size() >= 3) {
                    // Set the first and second categories
                    product.setCampaign(categoryPathNode.path(0).path("name").asText());
                    product.setSubCategory(categoryPathNode.path(2).path("name").asText());
                } else {
                    // Handle the case where categoryPath is not as expected
                    // You may set default values or log a warning/error
                }
                product.setCategory("Electronics");
                product.setRegularPrice(productNode.path("regularPrice").asText());
                product.setSalePrice(productNode.path("salePrice").asText());
                product.setDiscountPercentage(getDiscount(productNode.path("regularPrice").asText(), productNode.path("salePrice").asText()));
                
                product.setUrl(productNode.path("url").asText());
                product.setImage(productNode.path("image").asText());
                product.setRating(productNode.path("customerReviewAverage").asText());
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
    public void fetchDataAndSaveBestBuyLaptops3() throws JsonMappingException, JsonProcessingException {
        String bestBuyLaptopUrl = "https://api.bestbuy.com/v1/products(onSale=true&preowned=false&(categoryPath.id=abcat0502000))?apiKey=2ZNAIGLk2odeefyi3cBhGFxZ&pageSize=100&page=3&format=json";
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.add("Accept", "application/json");

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                bestBuyLaptopUrl, HttpMethod.GET, httpEntity, String.class);

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
                if (categoryPathNode.isArray() && categoryPathNode.size() >= 3) {
                    // Set the first and second categories
                    product.setCampaign(categoryPathNode.path(0).path("name").asText());
                    product.setSubCategory(categoryPathNode.path(2).path("name").asText());
                } else {
                    // Handle the case where categoryPath is not as expected
                    // You may set default values or log a warning/error
                }
                product.setCategory("Electronics");
                product.setRegularPrice(productNode.path("regularPrice").asText());
                product.setSalePrice(productNode.path("salePrice").asText());
                product.setDiscountPercentage(getDiscount(productNode.path("regularPrice").asText(), productNode.path("salePrice").asText()));
                
                product.setUrl(productNode.path("url").asText());
                product.setImage(productNode.path("image").asText());
                product.setRating(productNode.path("customerReviewAverage").asText());
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
    public void fetchDataAndSaveBestBuyLaptops4() throws JsonMappingException, JsonProcessingException {
        String bestBuyLaptopUrl = "https://api.bestbuy.com/v1/products(onSale=true&preowned=false&(categoryPath.id=abcat0502000))?apiKey=2ZNAIGLk2odeefyi3cBhGFxZ&pageSize=100&page=4&format=json";
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.add("Accept", "application/json");

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                bestBuyLaptopUrl, HttpMethod.GET, httpEntity, String.class);

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
                if (categoryPathNode.isArray() && categoryPathNode.size() >= 3) {
                    // Set the first and second categories
                    product.setCampaign(categoryPathNode.path(0).path("name").asText());
                    product.setSubCategory(categoryPathNode.path(2).path("name").asText());
                } else {
                    // Handle the case where categoryPath is not as expected
                    // You may set default values or log a warning/error
                }
                product.setCategory("Electronics");
                product.setRegularPrice(productNode.path("regularPrice").asText());
                product.setSalePrice(productNode.path("salePrice").asText());
                product.setDiscountPercentage(getDiscount(productNode.path("regularPrice").asText(), productNode.path("salePrice").asText()));
                
                product.setUrl(productNode.path("url").asText());
                product.setImage(productNode.path("image").asText());
                product.setRating(productNode.path("customerReviewAverage").asText());
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
    public void fetchDataAndSaveBestBuyLaptops5() throws JsonMappingException, JsonProcessingException {
        String bestBuyLaptopUrl = "https://api.bestbuy.com/v1/products(onSale=true&preowned=false&(categoryPath.id=abcat0502000))?apiKey=2ZNAIGLk2odeefyi3cBhGFxZ&pageSize=100&page=5&format=json";
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.add("Accept", "application/json");

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                bestBuyLaptopUrl, HttpMethod.GET, httpEntity, String.class);

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
                if (categoryPathNode.isArray() && categoryPathNode.size() >= 3) {
                    // Set the first and second categories
                    product.setCampaign(categoryPathNode.path(0).path("name").asText());
                    product.setSubCategory(categoryPathNode.path(2).path("name").asText());
                } else {
                    // Handle the case where categoryPath is not as expected
                    // You may set default values or log a warning/error
                }
                product.setCategory("Electronics");
                product.setRegularPrice(productNode.path("regularPrice").asText());
                product.setSalePrice(productNode.path("salePrice").asText());
                product.setDiscountPercentage(getDiscount(productNode.path("regularPrice").asText(), productNode.path("salePrice").asText()));
                
                product.setUrl(productNode.path("url").asText());
                product.setImage(productNode.path("image").asText());
                product.setRating(productNode.path("customerReviewAverage").asText());
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
    public void fetchDataAndSaveBestBuyDesktops() throws JsonMappingException, JsonProcessingException {
        String bestBuyLaptopUrl = "https://api.bestbuy.com/v1/products(onSale=true&(categoryPath.id=abcat0501000))?apiKey=2ZNAIGLk2odeefyi3cBhGFxZ&pageSize=100&format=json";
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.add("Accept", "application/json");

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                bestBuyLaptopUrl, HttpMethod.GET, httpEntity, String.class);

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
                if (categoryPathNode.isArray() && categoryPathNode.size() >= 3) {
                    // Set the first and second categories
                    product.setCampaign(categoryPathNode.path(0).path("name").asText());
                    product.setSubCategory(categoryPathNode.path(2).path("name").asText());
                } else {
                    // Handle the case where categoryPath is not as expected
                    // You may set default values or log a warning/error
                }
                product.setCategory("Electronics");
                product.setRegularPrice(productNode.path("regularPrice").asText());
                product.setSalePrice(productNode.path("salePrice").asText());
                product.setDiscountPercentage(getDiscount(productNode.path("regularPrice").asText(), productNode.path("salePrice").asText()));
                
                product.setUrl(productNode.path("url").asText());
                product.setImage(productNode.path("image").asText());
                product.setRating(productNode.path("customerReviewAverage").asText());
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
    public void fetchDataAndSaveBestBuyTVs() throws JsonMappingException, JsonProcessingException {
        String bestBuyLaptopUrl = "https://api.bestbuy.com/v1/products(onSale=true&preowned=false&(categoryPath.id=abcat0101000))?apiKey=2ZNAIGLk2odeefyi3cBhGFxZ&pageSize=100&format=json";
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.add("Accept", "application/json");

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                bestBuyLaptopUrl, HttpMethod.GET, httpEntity, String.class);

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
                if (categoryPathNode.isArray() && categoryPathNode.size() >= 3) {
                    // Set the first and second categories
                    product.setCampaign(categoryPathNode.path(0).path("name").asText());
                    product.setSubCategory(categoryPathNode.path(2).path("name").asText());
                } else {
                    // Handle the case where categoryPath is not as expected
                    // You may set default values or log a warning/error
                }
                product.setCategory("Electronics");
                product.setRegularPrice(productNode.path("regularPrice").asText());
                product.setSalePrice(productNode.path("salePrice").asText());
                product.setDiscountPercentage(getDiscount(productNode.path("regularPrice").asText(), productNode.path("salePrice").asText()));
                
                product.setUrl(productNode.path("url").asText());
                product.setImage(productNode.path("image").asText());
                product.setRating(productNode.path("customerReviewAverage").asText());
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
    public void fetchDataAndSaveBestBuyMobiles() throws JsonMappingException, JsonProcessingException {
        String bestBuyLaptopUrl = "https://api.bestbuy.com/v1/products(onSale=true&preowned=false&(categoryPath.id=pcmcat209400050001))?apiKey=2ZNAIGLk2odeefyi3cBhGFxZ&pageSize=100&format=json";
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.add("Accept", "application/json");

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                bestBuyLaptopUrl, HttpMethod.GET, httpEntity, String.class);

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
                if (categoryPathNode.isArray() && categoryPathNode.size() >= 3) {
                    // Set the first and second categories
                    product.setCampaign(categoryPathNode.path(0).path("name").asText());
                    product.setSubCategory(categoryPathNode.path(1).path("name").asText());
                } else {
                    // Handle the case where categoryPath is not as expected
                    // You may set default values or log a warning/error
                }
                product.setCategory("Electronics");
                product.setRegularPrice(productNode.path("regularPrice").asText());
                product.setSalePrice(productNode.path("salePrice").asText());
                product.setDiscountPercentage(getDiscount(productNode.path("regularPrice").asText(), productNode.path("salePrice").asText()));
                product.setUrl(productNode.path("url").asText());
                product.setImage(productNode.path("image").asText());
                product.setRating(productNode.path("customerReviewAverage").asText());
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

    private String getDiscount(String regularPriceStr, String salePriceStr) {
        if (regularPriceStr == null || regularPriceStr.isEmpty() || salePriceStr == null || salePriceStr.isEmpty()) {
            return "";
        }

        double regularPrice = Double.parseDouble(regularPriceStr);
        double salePrice = Double.parseDouble(salePriceStr);

        double discount = regularPrice - salePrice;
        double discountPercentage = (discount / regularPrice) * 100;

        return String.format("%.0f", discountPercentage) + "%";
    }

}
