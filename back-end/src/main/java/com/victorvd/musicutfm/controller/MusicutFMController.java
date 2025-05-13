package com.victorvd.musicutfm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victorvd.musicutfm.service.LastFMService;
import com.victorvd.musicutfm.service.MusicutFMService;


@RequestMapping("/home")
@RestController
public class MusicutFMController {

    @Autowired
    MusicutFMService apiAuth = new MusicutFMService();
    LastFMService apiRequest = new LastFMService();


    @GetMapping("/apitest")
    public Object welcome() {
        String key = apiAuth.getAPIKey();

        return apiRequest.getTrackGeneric(key, "duration", "cher", "believe");
        // return api.getTrackImage("image", 3, "cher", "believe");
    }
}
