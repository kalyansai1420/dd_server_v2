package com.dod.dodbackend.service;

import java.io.IOException;

public interface DellService {


    public void fetchDataAndSaveDellLaptops() throws IOException;

    public void fetchDataAndSaveDellPCs() throws IOException;
    
    public void fetchDataAndSaveDellGaming() throws IOException;
}
