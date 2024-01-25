package com.dod.dodbackend.service;

import java.io.IOException;
import java.util.List;

import com.dod.dodbackend.model.Vacation;


public interface VacationService {
    
    public void fetchDataAndSaveVacation() throws IOException;

    public List<Vacation> getVacationDeals();
}
