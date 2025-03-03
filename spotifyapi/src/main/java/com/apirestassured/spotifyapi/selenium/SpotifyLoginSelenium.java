package com.apirestassured.spotifyapi.selenium;


import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class SpotifyLoginSelenium {

    private final WebDriver driver;

    public SpotifyLoginSelenium(WebDriver driver) {
        this.driver = driver;
    }

    public String getCodeAfterRedirect(String url) {
        driver.get(url);

        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String fullUrlWithCode = driver.getCurrentUrl();
        return extractCodeFromUrl(fullUrlWithCode);
    }

    // Метод для извлечения "code" из URL
    private String extractCodeFromUrl(String url) {
        String[] parts = url.split("\\?");
        if (parts.length > 1) {
            String[] queryParams = parts[1].split("&");
            for (String param : queryParams) {
                if (param.startsWith("code=")) {
                    return param.split("=")[1];
                }
            }
        }
        return null;
    }
}
