package com.victorvd.musicutfm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victorvd.musicutfm.service.MusicutFMService;


@RequestMapping("/home")
@RestController
public class MusicutFMController {

    @Autowired
    LastFMController api = new LastFMController();


    @GetMapping("/apitest")
    public Object welcome() {
        MusicutFMService apiRequest = new MusicutFMService();
        
        String key = apiRequest.getAPIKey();

        return api.getTrackGeneric(key, "duration", "cher", "believe");
        // return api.getTrackImage("image", 3, "cher", "believe");
    }
}
