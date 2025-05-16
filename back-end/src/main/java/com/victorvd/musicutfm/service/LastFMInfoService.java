package com.victorvd.musicutfm.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;


@Service
public class LastFMInfoService{

    private final WebClient webClient;

    public LastFMInfoService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://ws.audioscrobbler.com").build();
    }

    public JSONObject getTrackObject(String key, String trackMbid) {
        try {            
            JSONObject trackData = getTrackData(key, trackMbid);

            return trackData;
        } catch (JSONException e) {
            System.out.println("Error: Invalid JSON structure or attribute not found - " + e.getMessage());

            return null;
        } catch (Exception e) {
            System.out.println("Error: An unexpected error occurred - " + e.getMessage());

            return null;
        }
    }

    public String getTrackString(String key, String getAttribute, String trackMbid) {
        try {            
            JSONObject trackData = getTrackData(key, trackMbid);

            if (!"all".equals(getAttribute)) {
                if (trackData.get(getAttribute) instanceof String) {
                    return trackData.getString(getAttribute);
                } else {
                    return trackData.get(getAttribute).toString();
                }
            } else {
                return trackData.toString();
            }
        } catch (JSONException e) {
            System.out.println("Error: Invalid JSON structure or attribute not found - " + e.getMessage());

            return null;
        } catch (Exception e) {
            System.out.println("Error: An unexpected error occurred - " + e.getMessage());

            return null;
        }
    }

    public String getTrackImage(String key, String trackMbid){
            try {
                JSONObject imageData = getTrackData(key, trackMbid).getJSONObject("album");
                
                org.json.JSONArray imageArray = imageData.getJSONArray("image");
                return imageArray.getJSONObject(imageArray.length() - 1).getString("#text");
                     
            } catch (JSONException e) {
                System.out.println("Error: Invalid JSON structure or attribute not found - " + e.getMessage());
                return null;
            } catch (Exception e) {
                System.out.println("Error: An unexpected error occurred - " + e.getMessage());
                return null;
            }
    }

    public JSONObject createCall(String key, String trackMbid) {
        try {
            Mono<String> response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/2.0/")
                            .queryParam("method", "track.getInfo")
                            .queryParam("api_key", key)
                            .queryParam("mbid", trackMbid)
                            .queryParam("format", "json")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class);

            String responseBody = response.block(); // síncrono (pode usar assíncrono se quiser)
            return new JSONObject(responseBody);

        } catch (Exception e) {
            System.out.println("Error fetching data from Last.fm: " + e.getMessage());
            return null;
        }
    }

    public JSONObject getTrackData(String key, String trackMbid) {
        
            JSONObject dataObject = createCall(key, trackMbid).getJSONObject("track");

            if (dataObject == null) {
                System.out.println("Error: Unable to fetch data from the API.");

                return null;
            }

            return dataObject;
    }

    // public JSONObject createCall(String key, String artist, String track) {
    //     try {
    //         Mono<String> response = webClient.get()
    //                 .uri(uriBuilder -> uriBuilder
    //                         .path("/2.0/")
    //                         .queryParam("method", "track.getInfo")
    //                         .queryParam("api_key", key)
    //                         .queryParam("artist", artist)
    //                         .queryParam("track", track)
    //                         .queryParam("format", "json")
    //                         .build())
    //                 .retrieve()
    //                 .bodyToMono(String.class);

    //         String responseBody = response.block(); // síncrono (pode usar assíncrono se quiser)
    //         return new JSONObject(responseBody);

    //     } catch (Exception e) {
    //         System.out.println("Error fetching data from Last.fm: " + e.getMessage());
    //         return null;
    //     }
    // }

    // public JSONObject getTrackData(String key, String artist, String track) {
        
    //         JSONObject dataObject = createCall(key, artist, track).getJSONObject("track");

    //         if (dataObject == null) {
    //             System.out.println("Error: Unable to fetch data from the API.");

    //             return null;
    //         }

    //         return dataObject;
    // }
}