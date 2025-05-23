package com.victorvd.musicutfm.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

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
            System.err.println("Process failed with exit code: " + exitCode);
            System.err.println("Command: " + String.join(" ", pb.command()));
            System.err.println("Error output:\n" + error);
            throw new RuntimeException("Process exited with code: " + exitCode + "\nError output:\n" + error);
        }
        process.getInputStream().close();
        process.getErrorStream().close();
    }

    public void downloadMp4(String videoID) throws Exception {
        ProcessBuilder pb = new ProcessBuilder();

        pb.command("cmd.exe", "/c", "src\\main\\java\\com\\victorvd\\musicutfm\\scripts\\downloadMp4.bat", videoID);

        Process process = pb.start();
        
        // readLines(process);

        String result = read(process.getInputStream());
        String error = read(process.getErrorStream());

        System.out.println(result);
        if (!error.isEmpty()) {
            System.err.println("Error output:\n" + error);
        }
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            System.err.println("Process failed with exit code: " + exitCode);
            System.err.println("Command: " + String.join(" ", pb.command()));
            System.err.println("Error output:\n" + error);
            throw new RuntimeException("Process exited with code: " + exitCode + "\nError output:\n" + error);
        }
        process.getInputStream().close();
        process.getErrorStream().close();
    }
    private static String read(java.io.InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder builder = new StringBuilder();
            String line = "0";
            while (line != null) {
                line = reader.readLine();
                System.out.println(line);
                builder.append(line);
                builder.append(System.lineSeparator());
            }
            return builder.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void readLines(Process process){
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String concatenatedString = reader.lines().collect(Collectors.joining(" "));
        System.out.println(concatenatedString);
    }
    
}
