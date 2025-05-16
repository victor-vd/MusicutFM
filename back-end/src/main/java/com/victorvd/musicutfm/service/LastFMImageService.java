package com.victorvd.musicutfm.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service
public class LastFMImageService {

    public void downloadCover(String imageUrl, String videoID) throws IOException {
        URL url = java.net.URI.create(imageUrl).toURL();

        ByteArrayOutputStream out;

        try (InputStream in = new BufferedInputStream(url.openStream())) {
            out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1!=(n=in.read(buf))){
                out.write(buf, 0, n);
            }   out.close();
        }
        
        byte[] response = out.toByteArray();

        try (FileOutputStream fos = new FileOutputStream("src/main/java/com/victorvd/musicutfm/media/temp/cover/lastfm-" + videoID + ".png")) {
            fos.write(response);
        }

        // CloseableHttpClient client = HttpClientBuilder.create().build();

        // // Example: https://lastfm.freetls.fastly.net/i/u/34s/3b54885952161aaea4ce2965b2db1638.png
        // HttpGet request = new HttpGet(url); 

        // request.addHeader("content-type", "image/png");
        // try (CloseableHttpResponse response = client.execute(request)) {
        //     HttpEntity entity = response.getEntity();
        //     if (entity != null) {
        //         try (FileOutputStream outstream = new FileOutputStream(new File("lastfm-" + videoID + ".png"))) {
        //             entity.writeTo(outstream);
        //         }
        //     }
        // }
    }
}
