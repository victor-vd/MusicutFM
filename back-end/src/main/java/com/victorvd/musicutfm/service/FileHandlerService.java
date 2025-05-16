package com.victorvd.musicutfm.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

@Service
public class FileHandlerService {
    public void downloadMusic(String videoID) throws Exception {
        ProcessBuilder pb = new ProcessBuilder();

        pb.command("cmd.exe", "/c", "src\\main\\java\\com\\victorvd\\musicutfm\\scripts\\downloadMusic.bat", videoID);

        Process process = pb.start();

        String result = read(process.getInputStream());
        String error = read(process.getErrorStream());

        System.out.println(result);
        if (!error.isEmpty()) {
            System.err.println("Error output:\n" + error);
        }
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Process exited with code: " + exitCode);
        }
        process.getInputStream().close();
        process.getErrorStream().close();
        process.getOutputStream().close();
    }

    private static String read(java.io.InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.lineSeparator());
            }
            return builder.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
