package com.victorvd.musicutfm.controller;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.victorvd.musicutfm.service.LastFMImageService;
import com.victorvd.musicutfm.service.LastFMInfoService;
import com.victorvd.musicutfm.service.LastFMSearchService;
import com.victorvd.musicutfm.service.MusicutFMService;


@RequestMapping("/home")
@RestController
public class MusicutFMController {

    @Autowired
    MusicutFMService apiAuth = new MusicutFMService();

    String key = apiAuth.getLastFmApiKey(); 

    private final LastFMInfoService lastFMInfoService;

    private final LastFMImageService lastFMImageService;

    private final LastFMSearchService lastFMSearchService;

    public MusicutFMController(LastFMInfoService lastFMInfoService, LastFMSearchService lastFMSearchService, LastFMImageService lastFMImageService) {
        this.lastFMInfoService = lastFMInfoService;
        this.lastFMSearchService = lastFMSearchService;
        this.lastFMImageService = lastFMImageService;
    }

    @GetMapping("/apitest")
    public Object getTrackInfo(@RequestParam String trackMbid) throws JsonProcessingException {
        JSONObject jsonObject = lastFMInfoService.getTrackObject(key, trackMbid);
        System.out.println(jsonObject);
        return jsonObject;
    }

    @GetMapping("/lastfmSearch")
    public ResponseEntity<String> searchLastFM(@RequestParam String track) {
        String result = lastFMSearchService.searchLastFM(key, track);
        System.out.println(result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/musicutfm/create")
    public void musicutFMCreate(@RequestParam String trackMbid, @RequestParam String videoID) {
        String imageURL = lastFMInfoService.getTrackImage(key, trackMbid);
        
        try {
            lastFMImageService.downloadCover(key, imageURL);
        } catch (IOException e) {
            // Log the error or handle it appropriately
            System.err.println("Error downloading cover: " + e.getMessage());
        }
    }
}
