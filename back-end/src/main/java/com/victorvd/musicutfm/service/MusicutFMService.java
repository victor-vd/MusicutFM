package com.victorvd.musicutfm.service;

import org.springframework.stereotype.Service;

@Service
public class MusicutFMService {
    
    public String getLastFmApiKey () {
        String apiKey = System.getenv("MUSICUTFM_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            System.out.println("Error: API key is not set in the environment variables.");
            return null;
        } else {
            return apiKey;
        }
    }
    
    public String getYtDataApiKey () {
        String apiKey = System.getenv("YOUTUBE_DATA_API_V3_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            System.out.println("Error: API key is not set in the environment variables.");
            return null;
        } else {
            return apiKey;
        }
    }
}
