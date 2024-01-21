package com.dod.dodbackend.repo.impl;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dod.dodbackend.repo.ProductCustomRepo;

@Repository
public class ProductCustomRepoImpl implements ProductCustomRepo{
	
	@Autowired
    private EntityManager entityManager;
	
    @Transactional
	@Override
	public void createIFullTextndex() {
		 String sql = "CREATE FULLTEXT INDEX productsIndex ON doddb.product(name);";
	        entityManager.createNativeQuery(sql).executeUpdate();
	}

}
