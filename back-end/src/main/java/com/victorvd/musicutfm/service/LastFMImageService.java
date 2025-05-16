package com.victorvd.musicutfm.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

@Service
public class LastFMImageService {

    public void downloadCover(String url, String trackMbid) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();

        // Example: https://lastfm.freetls.fastly.net/i/u/34s/3b54885952161aaea4ce2965b2db1638.png
        HttpGet request = new HttpGet(url); 

        request.addHeader("content-type", "image/png");
        try (CloseableHttpResponse response = client.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try (FileOutputStream outstream = new FileOutputStream(new File(trackMbid + ".png"))) {
                    entity.writeTo(outstream);
                }
            }
        }
    }
}
