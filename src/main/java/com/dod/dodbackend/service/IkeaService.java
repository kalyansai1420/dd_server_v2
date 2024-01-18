package com.dod.dodbackend.service;

import java.io.IOException;

public interface IkeaService {

    public void fetchDataAndSaveIkeaSofas() throws IOException;
    
    public void fetchDataAndSaveIkeaTables() throws IOException;

    public void fetchDataAndSaveIkeaTVCabinets() throws IOException;
    
    public void fetchDataAndSaveIkeaChairs() throws IOException;
    
    
    
}
