package com.victorvd.musicutfm.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class YTSearchService {

    private final String BASE_URL = "https://www.googleapis.com/youtube/v3";

    private final WebClient webClient;

    public YTSearchService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl(BASE_URL).build();
    }

    public String searchVideos(String API_KEY, String query, int maxResults) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("part", "snippet")
                        .queryParam("query", query)
                        .queryParam("maxResults", maxResults)
                        .queryParam("type", "video")
                        .queryParam("key", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block(); // or return a Mono if you want it reactive
    }
}

