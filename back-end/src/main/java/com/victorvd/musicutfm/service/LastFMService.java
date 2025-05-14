package com.victorvd.musicutfm.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;


@Service
public class LastFMService{

    public LastFMService () {}

    public Object getTrackGeneric(String key, String getAttribute, String artist, String track) {
        ArrayList<String> jsonArrayList = new ArrayList<>();

        try {
            JSONObject dataObject = createCall(key, artist, track);

            if (dataObject == null) {
                System.out.println("Error: Unable to fetch data from the API.");
                return null;
            }
            
            JSONObject trackData = dataObject.getJSONObject("track");

            if (!"all".equals(getAttribute)) {
                if (trackData.get(getAttribute) instanceof JSONObject) {
                    return trackData.getJSONObject(getAttribute);
                } else if (trackData.get(getAttribute) instanceof JSONArray) {

                    JSONArray jsonArray = trackData.getJSONArray(getAttribute);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonArrayList.add(jsonArray.get(i).toString());
                    }

                    return jsonArrayList;
                } else {
                    return trackData.get(getAttribute);
                }
            } else {
                return dataObject;
            }
        } catch (JSONException e) {
            System.out.println("Error: Invalid JSON structure or attribute not found - " + e.getMessage());

            return null;
        } catch (Exception e) {
            System.out.println("Error: An unexpected error occurred - " + e.getMessage());

            return null;
        }
    }

    public Object getTrackArray(String key, String getAttribute, String artist, String track) {
        ArrayList<String> jsonArrayList = new ArrayList<>();

        try {
            JSONObject dataObject = createCall(key, artist, track);

            if (dataObject == null) {
                System.out.println("Error: Unable to fetch data from the API.");

                return null;
            }

            JSONObject trackData = dataObject.getJSONObject("track");
            
            if (!"all".equals(getAttribute)) {
                if (trackData.get(getAttribute) instanceof JSONArray) {

                    JSONArray jsonArray = trackData.getJSONArray(getAttribute);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonArrayList.add(jsonArray.get(i).toString());
                    }

                    return jsonArrayList;
                } else {
                    System.out.println("Error: The specified attribute is not a JSON array.");
                    return null;
                }
            } else {
                return dataObject;
            }
        } catch (JSONException e) {
            System.out.println("Error: Invalid JSON structure or attribute not found - " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Error: An unexpected error occurred - " + e.getMessage());
            return null;
        }
    }

    public Object getTrackImage(String key, String getAttribute, int quality, String artist, String track){
        if ("image".equals(getAttribute)) {
            try {
                JSONObject dataObject = createCall(key, artist, track);
            
                if (dataObject == null) {
                    System.out.println("Error: Unable to fetch data from the API.");
                    return null;
                }

                JSONObject imageData = dataObject.getJSONObject("track").getJSONObject("album");
                
                if(quality >= 1 && quality <= 4){
                    return imageData.getJSONArray("image").getJSONObject(quality).getString("#text");      
                }else{
                    return imageData.getString("image");      
                }
            } catch (JSONException e) {
                System.out.println("Error: Invalid JSON structure or attribute not found - " + e.getMessage());
                return null;
            } catch (Exception e) {
                System.out.println("Error: An unexpected error occurred - " + e.getMessage());
                return null;
            }
        } else {
            System.out.println("Error: The specified attribute is not a last.fm image.");
            return null;
        }
    }

    public JSONObject createCall(String key, String artist, String track){
        try {
            System.out.println("test" + key);

            URL url;

            try {
                url = new URI("https", "ws.audioscrobbler.com", "/2.0/", "method=track.getInfo&api_key=" + key + "&artist=" + artist + "&track=" + track + "&format=json", null).toURL();
            } catch (URISyntaxException ex) {
                System.out.println("Error: Invalid URI syntax - " + ex.getMessage());
                return null;
            }
            
            if (url == null) {
                System.out.println("Error: URL could not be initialized.");
                return null;
            }
                
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
        
            int responseCode = conn.getResponseCode();
        
            if(responseCode != 200){
                throw new RuntimeException("HttpResponseCode" + responseCode);
            } else {
                StringBuilder informationString = new StringBuilder();

                try (Scanner scanner = new Scanner(url.openStream())) {
                    while (scanner.hasNext()) {
                        informationString = informationString.append(scanner.nextLine());
                    }
                }
    
                JSONObject dataObject = new JSONObject(informationString.toString());
                
                return dataObject;
            }
        } catch (IOException | RuntimeException e) {
            return null;
        } 
    }
}