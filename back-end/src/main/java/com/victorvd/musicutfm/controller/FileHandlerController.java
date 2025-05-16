package com.victorvd.musicutfm.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.victorvd.musicutfm.service.FileHandlerService;


@RestController
@RequestMapping("/media")
public class FileHandlerController {

    @Autowired
    FileHandlerService fileHandler = new FileHandlerService();
    
    private final String BASE_DIR = "src/main/java/com/victorvd/musicutfm/media/temp/audio";

    @GetMapping("/audio")
    public void downloadAudioFile(@RequestParam String videoID) {
        try {
            fileHandler.downloadMusic(videoID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/download/{fileType}/{fileName}")
    public ResponseEntity<Resource> downloadVideoFile(@PathVariable String fileType, @PathVariable String fileName) {
        try {
            Path filePath = Paths.get(BASE_DIR+"/"+fileType)
                    .resolve(fileName)
                    .normalize();

            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
}