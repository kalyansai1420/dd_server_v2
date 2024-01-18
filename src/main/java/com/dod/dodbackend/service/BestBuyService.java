package com.dod.dodbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface BestBuyService {

    public void fetchDataAndSaveBestBuyLaptops() throws JsonMappingException, JsonProcessingException;

    public void fetchDataAndSaveBestBuyDesktops() throws JsonMappingException, JsonProcessingException;

    public void fetchDataAndSaveBestBuyTVs() throws JsonMappingException, JsonProcessingException;

    public void fetchDataAndSaveBestBuyMobiles() throws JsonMappingException, JsonProcessingException;

}
