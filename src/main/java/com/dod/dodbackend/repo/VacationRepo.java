package com.dod.dodbackend.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.dod.dodbackend.model.Vacation;


public interface VacationRepo extends JpaRepository<Vacation, Long>{

    public Vacation findByName(String name);

    
}
