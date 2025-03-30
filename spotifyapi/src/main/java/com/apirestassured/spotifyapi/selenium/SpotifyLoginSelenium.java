package com.apirestassured.spotifyapi.selenium;
import com.apirestassured.spotifyapi.apiClient.SpotifyClientService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SpotifyLoginSelenium {

    private WebElementHelper webElementHelper;
    private WebDriverWait wait;
    private final WebDriver driver;

    @FindBy(id = "login-username")
    private WebElement login;
    @FindBy(xpath = "//input[@id='login-password']")
    private WebElement password;
    @FindBy(xpath = "//button[@id='login-button']")
    private WebElement submitButton;
    @FindBy(xpath = ".//iframe[@title='reCAPTCHA']")
    private WebElement iframe;
    @FindBy(xpath = ".//span[@id='recaptcha-anchor']")
    private WebElement reCaptcha;
    @FindBy(xpath = ".//span[text()='Kontynuuj']")
    private WebElement continueButton;

    public SpotifyLoginSelenium (WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        webElementHelper = new WebElementHelper(wait);

        PageFactory.initElements(driver, this);
    }

    public String getCodeAfterRedirect(WebDriver driver, String url) {
        driver.get(url);
        enterLoginAndPassword();

        String fullUrlWithCode = driver.getCurrentUrl();

        return extractCodeFromUrl(fullUrlWithCode);
    }

    private SpotifyLoginSelenium enterLoginAndPassword() {
        login.click();
        login.sendKeys(SpotifyClientService.getLogin());

        if (webElementHelper.isElementPresent(password)) {
            password.sendKeys(SpotifyClientService.getPassword());
        }
        submitButton.click();

        if (webElementHelper.isElementPresent(reCaptcha)) {
            driver.switchTo().frame(iframe);
            reCaptcha.click();
            continueButton.click();
            driver.switchTo().defaultContent();
        }

        if (!driver.getCurrentUrl().startsWith("http://localhost:8080/")) {
            try {
                Thread.sleep(20000);
            } catch (Exception exception) {
                exception.getMessage();
            }
        }

        return this;
    }

    // method for extracting code from url
    private String extractCodeFromUrl(String url) {
        String regex = "code%3D([A-Za-z0-9\\-_]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
