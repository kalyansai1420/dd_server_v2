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
    
	@Query(value = "SELECT DISTINCT * FROM dddatabase.product where name LIKE %:search% LIMIT 25 ", nativeQuery = true)
	List<Product> searchByName(@Param("search") String search);
	
	
	@Query(value="SELECT DISTINCT COUNT(*) > 0 FROM information_schema.STATISTICS WHERE table_schema = 'dddatabase' AND table_name = 'product' AND index_name = 'productsIndex'",nativeQuery = true)
	public Integer showFullTextindex();
	
	@Query(value="SELECT DISTINCT * FROM dddatabase.product where MATCH(name) AGAINST (:search) LIMIT 25;", nativeQuery = true)
	List<Product>indexSearch(@Param("search") String search);

	// Deals on laptops
	@Query(value="Select * from dddatabase.product where sub_category = 'Laptops'", nativeQuery = true)
	List<Product> getLaptops();

	// Deals on Gaming and Monitor
	@Query(value="Select * from dddatabase.product where sub_category in ('Gaming','Desktops','Desktop & All-in-One Computers')", nativeQuery = true)
	List<Product> getGamingAndMonitor();

	// Top deals with highest discount percentage
	@Query(value="Select * from dddatabase.product where discount_percentage > 0 order by discount_percentage desc limit 50", nativeQuery = true)
	List<Product> getTopDeals();

	// Get furnitures
	@Query(value="Select * from dddatabase.product where campaign = 'Ikea'", nativeQuery = true)
	List<Product> getFurnitures();

	// Get Electronics
	@Query(value="Select * from dddatabase.product where category = 'Electronics'", nativeQuery = true)
	List<Product> getElectronics();

	// Get Vacation
    
} 