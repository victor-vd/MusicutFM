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
import com.victorvd.musicutfm.service.FileHandlerService;
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

    public MusicutFMController(LastFMInfoService lastFMInfoService, 
                                LastFMSearchService lastFMSearchService, 
                                LastFMImageService lastFMImageService) {
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
    public ResponseEntity<String> lastFMSearch(@RequestParam String track, @RequestParam String artist) {
        String result = lastFMSearchService.searchLastFMArtist(key, track, artist);
        System.out.println(result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/musicutfm/create")
    public ResponseEntity<String> musicutFMCreate(@RequestParam String trackMbid, @RequestParam String videoID) {
        String imageURL = lastFMInfoService.getTrackImage(key, trackMbid);

        System.out.println(imageURL);

        try {
            FileHandlerService fileHandlerService = new FileHandlerService();
            fileHandlerService.downloadMp4(videoID);
        } catch (Exception e) {
            System.err.println("Error downloading music video: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        try {
            lastFMImageService.downloadCover(imageURL, videoID);
        } catch (IOException e) {
            System.err.println("Error downloading cover: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        try {
            FileHandlerService fileHandlerService = new FileHandlerService();
            fileHandlerService.downloadMusic(videoID);
        } catch (Exception e) {
            System.err.println("Error downloading audio file: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        String htmlImageLink = "<img src=\"" + imageURL + "\" alt=\"Track Cover\" />";
        System.out.println(imageURL);
        return ResponseEntity.ok(htmlImageLink);
    }
}
