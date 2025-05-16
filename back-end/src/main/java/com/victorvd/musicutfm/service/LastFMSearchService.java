package com.victorvd.musicutfm.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class LastFMSearchService {

    private final String BASE_URL = "https://ws.audioscrobbler.com";

    private final WebClient webClient;

    public LastFMSearchService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl(BASE_URL).build();
    }

    public String searchLastFMArtist(String apiKey, String track, String artist) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/2.0")
                        .queryParam("method", "track.search")
                        .queryParam("track", track)
                        .queryParam("artist", artist)
                        .queryParam("api_key", apiKey)
                        .queryParam("limit", 5)
                        .queryParam("format", "json")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String searchLastFM(String apiKey, String track) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/2.0")
                        .queryParam("method", "track.search")
                        .queryParam("track", track)
                        .queryParam("api_key", apiKey)
                        .queryParam("limit", 10)
                        .queryParam("format", "json")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
