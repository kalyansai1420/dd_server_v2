package com.dod.dodbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dod.dodbackend.model.WalmartCatalogs;

@Repository
public interface WalmartCatalogsRepo extends JpaRepository<WalmartCatalogs, Integer>{

	public WalmartCatalogs findById(String id);
}
