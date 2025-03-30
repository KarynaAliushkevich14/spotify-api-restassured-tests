package com.apirestassured.spotifyapi.selenium;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebElementHelper {

    private WebDriverWait wait;

    public WebElementHelper (WebDriverWait wait) {
        this.wait = wait;
    }

    // czy element pojawia się na stronie
    public boolean isElementPresent(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (Exception exception) {
            return false;
        }
    }
}
