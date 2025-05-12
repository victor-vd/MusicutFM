package com.victorvd.musicutfm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class MusicutfmApplication {

	@RequestMapping("/")
	String home() {
        System.out.println("1111111111111111");
		return "<h2>Hello World!!</h2>";
	}

	public static void main(String[] args) {
		SpringApplication.run(MusicutfmApplication.class, args);
	}

}
