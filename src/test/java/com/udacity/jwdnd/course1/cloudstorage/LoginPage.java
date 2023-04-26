package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final WebDriver webDriver;
    private final int port;

    public LoginPage(WebDriver webDriver, int port) {
        this.webDriver = webDriver;
        this.port = port;
    }

    public void doLogIn(String TEST_USERNAME, String TEST_PASSWORD) {
        webDriver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 3);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = webDriver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(TEST_USERNAME);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = webDriver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(TEST_PASSWORD);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));
    }
}
