package com.dod.dodbackend.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dod.dodbackend.model.Product;
import com.dod.dodbackend.model.ProductImage;
import com.dod.dodbackend.repo.ProductRepo;
import com.dod.dodbackend.service.DellService;

@Service
public class DellServiceImpl implements DellService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public void fetchDataAndSaveDellLaptops() throws IOException {
        String dellUrl = "https://www.dell.com/en-us/shop/deals/pc-laptop-deals";
        try {
            Document dellDocument = Jsoup.connect(dellUrl)
                    .userAgent(
                            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
                    .header("Referer", "https://www.google.com/")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .get();

            // Introduce a delay between requests
            try {
                Thread.sleep(2000); // 2 seconds delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String dellMainClass = "section.ps-top";
            Elements dellDeals = dellDocument.select(dellMainClass);

            List<Product> productsToSave = new ArrayList<>();
            for (Element productElement : dellDeals) {
                Product product = new Product();

                String description = productElement.select("div.ps-desc p").text();

                product.setSku(productElement.select("div.ps-oc.quick-view-only").text().replace("Order Code ", ""));

                product.setName(productElement.select("h3.ps-title a").text());

                product.setDescription(description);
                product.setSubCategory("Laptops");
                product.setCampaign("Dell");

                String regularPriceText = productElement.select("div.ps-orig span.strike-through").text();
                float regularPrice = Float.parseFloat(regularPriceText.replaceAll("[^0-9.]", ""));
                String formattedRegularPrice = String.format("%.2f", regularPrice);
                product.setRegularPrice(formattedRegularPrice);

                String salePriceText = productElement.select("div.ps-dell-price span").text();
                float salePrice = Float.parseFloat(salePriceText.replaceAll("[^0-9.]", ""));
                String formattedSalePrice = String.format("%.2f", salePrice);
                product.setSalePrice(formattedSalePrice);

                product.setImage(productElement.select("div.ps-image img").attr("data-src"));

                String productUrlText = productElement.select("h3.ps-title a").attr("href");
                String productUrl = productUrlText.replace("//", "");
                product.setUrl(productUrl);

                String rawRating = productElement.select("div.ps-ratings-and-reviews span.ratings-text").text();
                String[] ratingParts = rawRating.split("\\s", 2); // Split at the first whitespace
                String rating = ratingParts[0];
                product.setRating(rating);

                product.setCategory("Electronics");

                product.setDiscountPercentage(getDiscount(formattedRegularPrice, formattedSalePrice));

                List<ProductImage> productImages = new ArrayList<>();
                String productImageURL = productElement.select("div.ps-image img").attr("data-src");
                if (!productImageURL.isEmpty()) {
                    ProductImage productImage = new ProductImage();
                    productImage.setImageUrl(productImageURL);
                    productImage.setProduct(product);
                    productImages.add(productImage);
                }

                product.setImages(productImages);

                productsToSave.add(product);
            }

            // Save the list of products to the database
            this.productRepo.saveAll(productsToSave);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void fetchDataAndSaveDellPCs() throws IOException {
        String dellUrl = "https://www.dell.com/en-us/shop/deals/pc-desktop-deals";
        try {
            Document dellDocument = Jsoup.connect(dellUrl)
                    .userAgent(
                            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
                    .header("Referer", "https://www.google.com/")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .get();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String dellMainClass = "section.ps-top";
            Elements dellDeals = dellDocument.select(dellMainClass);

            List<Product> productsToSave = new ArrayList<>();
            for (Element productElement : dellDeals) {
                Product product = new Product();

                String description = productElement.select("div.ps-desc p").text();

                product.setSku(productElement.select("div.ps-oc.quick-view-only").text().replace("Order Code ", ""));

                product.setName(productElement.select("h3.ps-title a").text());

                product.setDescription(description);
                product.setSubCategory("Desktops");
                product.setCampaign("Dell");

                String regularPriceText = productElement.select("div.ps-orig span.strike-through").text();
                float regularPrice = Float.parseFloat(regularPriceText.replaceAll("[^0-9.]", ""));
                String formattedRegularPrice = String.format("%.2f", regularPrice);
                product.setRegularPrice(formattedRegularPrice);

                String salePriceText = productElement.select("div.ps-dell-price span").text();
                float salePrice = Float.parseFloat(salePriceText.replaceAll("[^0-9.]", ""));
                String formattedSalePrice = String.format("%.2f", salePrice);
                product.setSalePrice(formattedSalePrice);

                product.setImage(productElement.select("div.ps-image img").attr("data-src"));

                String productUrlText = productElement.select("h3.ps-title a").attr("href");
                String productUrl = productUrlText.replace("//", "");
                product.setUrl(productUrl);

                String rawRating = productElement.select("div.ps-ratings-and-reviews span.ratings-text").text();
                String[] ratingParts = rawRating.split("\\s", 2); // Split at the first whitespace
                String rating = ratingParts[0];
                product.setRating(rating);

                product.setCategory("Electronics");
                product.setDiscountPercentage(getDiscount(formattedRegularPrice, formattedSalePrice));

                List<ProductImage> productImages = new ArrayList<>();
                String productImageURL = productElement.select("div.ps-image img").attr("data-src");
                if (!productImageURL.isEmpty()) {
                    ProductImage productImage = new ProductImage();
                    productImage.setImageUrl(productImageURL);
                    productImage.setProduct(product);
                    productImages.add(productImage);
                }

                product.setImages(productImages);

                productsToSave.add(product);
            }

            this.productRepo.saveAll(productsToSave);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fetchDataAndSaveDellGaming() throws IOException {

        String dellUrl = "https://www.dell.com/en-us/shop/deals/pc-gaming-deals";
        try {
            Document dellDocument = Jsoup.connect(dellUrl)
                    .userAgent(
                            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
                    .header("Referer", "https://www.google.com/")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .get();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String dellMainClass = "section.ps-top";
            Elements dellDeals = dellDocument.select(dellMainClass);

            List<Product> productsToSave = new ArrayList<>();
            for (Element productElement : dellDeals) {
                Product product = new Product();

                String description = productElement.select("div.ps-desc p").text();

                product.setSku(productElement.select("div.ps-oc.quick-view-only").text().replace("Order Code ", ""));

                product.setName(productElement.select("h3.ps-title a").text());

                product.setDescription(description);
                product.setSubCategory("Gaming");
                product.setCampaign("Dell");

                String regularPriceText = productElement.select("div.ps-orig span.strike-through").text();
                float regularPrice = regularPriceText.isEmpty() ? 0
                        : Float.parseFloat(regularPriceText.replaceAll("[^0-9.]", ""));
                String formattedRegularPrice = String.format("%.2f", regularPrice);
                product.setRegularPrice(formattedRegularPrice);

                String salePriceText = productElement.select("div.ps-dell-price span").text();
                float salePrice = salePriceText.isEmpty() ? 0 : Float.parseFloat(salePriceText.replaceAll("[^0-9.]", ""));
                String formattedSalePrice = String.format("%.2f", salePrice);
                product.setSalePrice(formattedSalePrice);

                product.setImage(productElement.select("div.ps-image img").attr("data-src"));

                String productUrlText = productElement.select("h3.ps-title a").attr("href");
                String productUrl = productUrlText.replace("//", "");
                product.setUrl(productUrl);

                String rawRating = productElement.select("div.ps-ratings-and-reviews span.ratings-text").text();
                String[] ratingParts = rawRating.split("\\s", 2); // Split at the first whitespace
                String rating = ratingParts[0];
                product.setRating(rating);

                product.setCategory("Electronics");
                product.setDiscountPercentage(getDiscount(formattedRegularPrice, formattedSalePrice));

                List<ProductImage> productImages = new ArrayList<>();
                String productImageURL = productElement.select("div.ps-image img").attr("data-src");
                if (!productImageURL.isEmpty()) {
                    ProductImage productImage = new ProductImage();
                    productImage.setImageUrl(productImageURL);
                    productImage.setProduct(product);
                    productImages.add(productImage);
                }

                product.setImages(productImages);

                productsToSave.add(product);
            }

            // Save the list of products to the database
            this.productRepo.saveAll(productsToSave);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Long getDiscount(String regularPriceStr, String salePriceStr) {
        if (regularPriceStr == null || regularPriceStr.isEmpty() || salePriceStr == null || salePriceStr.isEmpty()) {
            return 0L;
        }

        double regularPrice = Double.parseDouble(regularPriceStr);
        double salePrice = Double.parseDouble(salePriceStr);

        double discount = regularPrice - salePrice;
        double discountPercentage = (discount / regularPrice) * 100;

        return (long) discountPercentage;
    }

}
