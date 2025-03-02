package com.apirestassured.spotifyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.apirestassured.spotifyapi") // @Configuration + @ComponentScan + @EnableAutoConfiguration
public class SpotifyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpotifyApiApplication.class, args);
    }

}