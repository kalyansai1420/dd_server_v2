package com.dod.dodbackend.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dod.dodbackend.model.Product;
import com.dod.dodbackend.model.ProductImage;
import com.dod.dodbackend.repo.ProductRepo;
import com.dod.dodbackend.service.IkeaService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class IkeaServiceImpl implements IkeaService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public void fetchDataAndSaveIkeaSofas() throws IOException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "https://ikea-api.p.rapidapi.com/categorySearch?categoryID=fu003&countryCode=us&languageCode=en"))
                    .header("X-RapidAPI-Key", "56f103722cmshf68e5820041464bp14b4afjsn96ca9569f0c9")
                    .header("X-RapidAPI-Host", "ikea-api.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response;
            try {
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();

                ObjectMapper objectMapper = new ObjectMapper();
                List<Map<String, Object>> productsList = objectMapper.readValue(responseBody,
                        new TypeReference<List<Map<String, Object>>>() {
                        });

                for (Map<String, Object> productMap : productsList) {
                    String id = (String) productMap.get("id");
                    String campaign = "Ikea";

                    Object categoryPathObject = productMap.get("categoryPath");

                    String category = "";
                    String subCategory = "";
                    if (categoryPathObject instanceof List) {
                        List<Map<String, String>> categoryPathList = (List<Map<String, String>>) categoryPathObject;

                        if (!categoryPathList.isEmpty()) {

                            category = categoryPathList.get(0).get("name");
                            subCategory = categoryPathList.get(1).get("name");

                        }
                    } else {
                        category = "Furniture";
                        subCategory = "Sofas";
                    }

                    String description = (String) productMap.get("imageAlt");

                    String image = (String) productMap.get("image");
                    String name = (String) productMap.get("name");
                    String rating = "N/A";

                    Object priceObject = productMap.get("price");
                    String salePriceStr = "";
                    String regularPriceStr = "";

                    if (priceObject instanceof Map) {
                        Map<String, Object> priceMap = (Map<String, Object>) priceObject;
                        Object currentPriceObject = priceMap.get("currentPrice");

                        if (currentPriceObject instanceof Double || currentPriceObject instanceof Integer) {
                            salePriceStr = String.valueOf(currentPriceObject);
                            regularPriceStr = String.valueOf(currentPriceObject);

                        }
                    }
                    String discountPercentage = getDiscount(regularPriceStr, salePriceStr);

                    String url = (String) productMap.get("url");

                    Product product = new Product();
                    product.setSku(id);
                    product.setCampaign(campaign);
                    product.setCategory(category);
                    product.setDescription(description);
                    product.setDiscountPercentage(discountPercentage);
                    product.setImage(image);
                    product.setName(name);
                    product.setRating(rating);
                    product.setRegularPrice(regularPriceStr);
                    product.setSalePrice(salePriceStr);
                    product.setSubCategory(subCategory);
                    product.setUrl(url);

                    List<ProductImage> productImages = new ArrayList<>();
                    String productImageURL = (String) productMap.get("image");
                    if (!productImageURL.isEmpty()) {
                        ProductImage productImage = new ProductImage();
                        productImage.setImageUrl(productImageURL);
                        productImage.setProduct(product);
                        productImages.add(productImage);
                    }

                    product.setImages(productImages);

                    productRepo.save(product);

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fetchDataAndSaveIkeaTables() throws IOException{
        try {
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "https://ikea-api.p.rapidapi.com/categorySearch?categoryID=fu004&countryCode=us&languageCode=en"))
                    .header("X-RapidAPI-Key", "56f103722cmshf68e5820041464bp14b4afjsn96ca9569f0c9")
                    .header("X-RapidAPI-Host", "ikea-api.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response;
            try {
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();

                ObjectMapper objectMapper = new ObjectMapper();
                List<Map<String, Object>> productsList = objectMapper.readValue(responseBody,
                        new TypeReference<List<Map<String, Object>>>() {
                        });

                for (Map<String, Object> productMap : productsList) {
                    String id = (String) productMap.get("id");
                    String campaign = "Ikea";

                    Object categoryPathObject = productMap.get("categoryPath");

                    String category = "";
                    String subCategory = "";
                    if (categoryPathObject instanceof List) {
                        List<Map<String, String>> categoryPathList = (List<Map<String, String>>) categoryPathObject;

                        if (!categoryPathList.isEmpty()) {

                            category = categoryPathList.get(0).get("name");
                            subCategory = categoryPathList.get(1).get("name");

                        }
                    } else {
                        category = "Furniture";
                        subCategory = "Tables";
                    }

                    String description = (String) productMap.get("imageAlt");

                    String image = (String) productMap.get("image");
                    String name = (String) productMap.get("name");
                    String rating = "N/A";

                    Object priceObject = productMap.get("price");
                    String salePriceStr = "";
                    String regularPriceStr = "";

                    if (priceObject instanceof Map) {
                        Map<String, Object> priceMap = (Map<String, Object>) priceObject;
                        Object currentPriceObject = priceMap.get("currentPrice");

                        if (currentPriceObject instanceof Double || currentPriceObject instanceof Integer) {
                            salePriceStr = String.valueOf(currentPriceObject);
                            regularPriceStr = String.valueOf(currentPriceObject);

                        }
                    }
                    String discountPercentage = getDiscount(regularPriceStr, salePriceStr);

                    String url = (String) productMap.get("url");

                    Product product = new Product();
                    product.setSku(id);
                    product.setCampaign(campaign);
                    product.setCategory(category);
                    product.setDescription(description);
                    product.setDiscountPercentage(discountPercentage);
                    product.setImage(image);
                    product.setName(name);
                    product.setRating(rating);
                    product.setRegularPrice(regularPriceStr);
                    product.setSalePrice(salePriceStr);
                    product.setSubCategory(subCategory);
                    product.setUrl(url);

                    List<ProductImage> productImages = new ArrayList<>();
                    String productImageURL = (String) productMap.get("image");
                    if (!productImageURL.isEmpty()) {
                        ProductImage productImage = new ProductImage();
                        productImage.setImageUrl(productImageURL);
                        productImage.setProduct(product);
                        productImages.add(productImage);
                    }

                    product.setImages(productImages);

                    productRepo.save(product);

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fetchDataAndSaveIkeaTVCabinets() throws IOException{
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "https://ikea-api.p.rapidapi.com/categorySearch?categoryID=10475&countryCode=us&languageCode=en"))
                    .header("X-RapidAPI-Key", "56f103722cmshf68e5820041464bp14b4afjsn96ca9569f0c9")
                    .header("X-RapidAPI-Host", "ikea-api.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response;
            try {
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();

                ObjectMapper objectMapper = new ObjectMapper();
                List<Map<String, Object>> productsList = objectMapper.readValue(responseBody,
                        new TypeReference<List<Map<String, Object>>>() {
                        });

                for (Map<String, Object> productMap : productsList) {
                    String id = (String) productMap.get("id");
                    String campaign = "Ikea";

                    Object categoryPathObject = productMap.get("categoryPath");

                    String category = "";
                    String subCategory = "";
                    if (categoryPathObject instanceof List) {
                        List<Map<String, String>> categoryPathList = (List<Map<String, String>>) categoryPathObject;

                        if (!categoryPathList.isEmpty()) {

                            category = categoryPathList.get(0).get("name");
                            subCategory = categoryPathList.get(1).get("name");

                        }
                    } else {
                        category = "Furniture";
                        subCategory = "TV Cabinets";
                    }

                    String description = (String) productMap.get("imageAlt");

                    String image = (String) productMap.get("image");
                    String name = (String) productMap.get("name");
                    String rating = "N/A";

                    Object priceObject = productMap.get("price");
                    String salePriceStr = "";
                    String regularPriceStr = "";

                    if (priceObject instanceof Map) {
                        Map<String, Object> priceMap = (Map<String, Object>) priceObject;
                        Object currentPriceObject = priceMap.get("currentPrice");

                        if (currentPriceObject instanceof Double || currentPriceObject instanceof Integer) {
                            salePriceStr = String.valueOf(currentPriceObject);
                            regularPriceStr = String.valueOf(currentPriceObject);

                        }
                    }
                    String discountPercentage = getDiscount(regularPriceStr, salePriceStr);

                    String url = (String) productMap.get("url");

                    Product product = new Product();
                    product.setSku(id);
                    product.setCampaign(campaign);
                    product.setCategory(category);
                    product.setDescription(description);
                    product.setDiscountPercentage(discountPercentage);
                    product.setImage(image);
                    product.setName(name);
                    product.setRating(rating);
                    product.setRegularPrice(regularPriceStr);
                    product.setSalePrice(salePriceStr);
                    product.setSubCategory(subCategory);
                    product.setUrl(url);

                    List<ProductImage> productImages = new ArrayList<>();
                    String productImageURL = (String) productMap.get("image");
                    if (!productImageURL.isEmpty()) {
                        ProductImage productImage = new ProductImage();
                        productImage.setImageUrl(productImageURL);
                        productImage.setProduct(product);
                        productImages.add(productImage);
                    }

                    product.setImages(productImages);

                    productRepo.save(product);

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fetchDataAndSaveIkeaChairs() throws IOException{
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "https://ikea-api.p.rapidapi.com/categorySearch?categoryID=od003&countryCode=us&languageCode=en"))
                    .header("X-RapidAPI-Key", "56f103722cmshf68e5820041464bp14b4afjsn96ca9569f0c9")
                    .header("X-RapidAPI-Host", "ikea-api.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response;
            try {
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();

                ObjectMapper objectMapper = new ObjectMapper();
                List<Map<String, Object>> productsList = objectMapper.readValue(responseBody,
                        new TypeReference<List<Map<String, Object>>>() {
                        });

                for (Map<String, Object> productMap : productsList) {
                    String id = (String) productMap.get("id");
                    String campaign = "Ikea";

                    Object categoryPathObject = productMap.get("categoryPath");

                    String category = "";
                    String subCategory = "";
                    if (categoryPathObject instanceof List) {
                        List<Map<String, String>> categoryPathList = (List<Map<String, String>>) categoryPathObject;

                        if (!categoryPathList.isEmpty()) {

                            category = categoryPathList.get(0).get("name");
                            subCategory = categoryPathList.get(1).get("name");

                        }
                    } else {
                        category = "Furniture";
                        subCategory = "Chairs";
                    }

                    String description = (String) productMap.get("imageAlt");

                    String image = (String) productMap.get("image");
                    String name = (String) productMap.get("name");
                    String rating = "N/A";

                    Object priceObject = productMap.get("price");
                    String salePriceStr = "";
                    String regularPriceStr = "";

                    if (priceObject instanceof Map) {
                        Map<String, Object> priceMap = (Map<String, Object>) priceObject;
                        Object currentPriceObject = priceMap.get("currentPrice");

                        if (currentPriceObject instanceof Double || currentPriceObject instanceof Integer) {
                            salePriceStr = String.valueOf(currentPriceObject);
                            regularPriceStr = String.valueOf(currentPriceObject);

                        }
                    }
                    String discountPercentage = getDiscount(regularPriceStr, salePriceStr);

                    String url = (String) productMap.get("url");

                    Product product = new Product();
                    product.setSku(id);
                    product.setCampaign(campaign);
                    product.setCategory(category);
                    product.setDescription(description);
                    product.setDiscountPercentage(discountPercentage);
                    product.setImage(image);
                    product.setName(name);
                    product.setRating(rating);
                    product.setRegularPrice(regularPriceStr);
                    product.setSalePrice(salePriceStr);
                    product.setSubCategory(subCategory);
                    product.setUrl(url);

                    List<ProductImage> productImages = new ArrayList<>();
                    String productImageURL = (String) productMap.get("image");
                    if (!productImageURL.isEmpty()) {
                        ProductImage productImage = new ProductImage();
                        productImage.setImageUrl(productImageURL);
                        productImage.setProduct(product);
                        productImages.add(productImage);
                    }

                    product.setImages(productImages);

                    productRepo.save(product);

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDiscount(String regularPriceStr, String salePriceStr) {
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
