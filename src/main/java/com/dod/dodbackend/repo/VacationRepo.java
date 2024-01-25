package com.dod.dodbackend.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dod.dodbackend.model.Vacation;


public interface VacationRepo extends JpaRepository<Vacation, Long>{

    public Vacation findByName(String name);

    @Query(value = "SELECT * FROM dddatabase.vacation", nativeQuery = true)
    List<Vacation> getVacationDeals();

    
}
