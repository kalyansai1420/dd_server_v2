package com.dod.dodbackend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dod.dodbackend.model.Product;
import com.dod.dodbackend.model.Vacation;
import com.dod.dodbackend.service.VacationService;

import org.springframework.web.bind.annotation.GetMapping;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class VacationController {
    
    @Autowired
    private VacationService vacationService;

    @GetMapping("/vacations")
    public String saveVacations() throws IOException {
        this.vacationService.fetchDataAndSaveVacation();
        return "saved";
    }
    
    @GetMapping("/vacationDeals")
    public ResponseEntity<List<Vacation>> getVacationDeals() {
		return ResponseEntity.ok(this.vacationService.getVacationDeals());
	}

}
