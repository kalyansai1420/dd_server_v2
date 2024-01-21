package com.dod.dodbackend.repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dod.dodbackend.model.Product;
import com.dod.dodbackend.util.WalmartProducts;
@Repository
public interface ProductRepo extends JpaRepository<Product, String>{

    public Product findBySku(String sku);
    
	@Query(value = "SELECT DISTINCT * FROM doddb.product where name LIKE %:search% LIMIT 25 ", nativeQuery = true)
	List<Product> searchByName(@Param("search") String search);
	
	
	@Query(value="SELECT DISTINCT COUNT(*) > 0 FROM information_schema.STATISTICS WHERE table_schema = 'doddb' AND table_name = 'product' AND index_name = 'productsIndex'",nativeQuery = true)
	public Integer showFullTextindex();
	
	@Query(value="SELECT DISTINCT * FROM doddb.product where MATCH(name) AGAINST (:search) LIMIT 25;", nativeQuery = true)
	List<Product>indexSearch(@Param("search") String search);
    

    
} 