package com.victorvd.musicutfm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.victorvd.musicutfm.service.MusicutFMService;
import com.victorvd.musicutfm.service.YTSearchService;

@RestController
@RequestMapping("/api/youtube")
public class YTSearchController {

    private final YTSearchService youTubeService;
    MusicutFMService apiAuth = new MusicutFMService();
    String key = apiAuth.getYtDataApiKey(); 

    public YTSearchController(YTSearchService youTubeService) {
        this.youTubeService = youTubeService;
    }

    @GetMapping("/YTsearch")
    public ResponseEntity<String> search(@RequestParam String query) {
        String result = youTubeService.searchVideos(key, query, 5);
        System.out.println(query);
        System.out.println(result);
        return ResponseEntity.ok(result);
    }
}
