package com.dod.dodbackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dod.dodbackend.model.Product;

public interface ProductRepo extends JpaRepository<Product, String>{

    public Product findBySku(String sku);

    List<Product> findBestBuyElectronics();

    
} 