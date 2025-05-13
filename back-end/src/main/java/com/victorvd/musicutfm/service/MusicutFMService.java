package com.victorvd.musicutfm.service;

import org.springframework.stereotype.Service;

@Service
public class MusicutFMService {
    
    public String getAPIKey () {
        String apiKey = System.getenv("MUSICUTFM_API_KEY");
        System.out.println(apiKey);

        if (apiKey == null || apiKey.isEmpty()) {
            System.out.println("Error: API key is not set in the environment variables.");
            return null;
        } else {
            return apiKey;
        }
    }
}
