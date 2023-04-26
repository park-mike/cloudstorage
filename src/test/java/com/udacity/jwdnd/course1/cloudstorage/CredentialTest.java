package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTest {

    public static final String WEB_TEST = "https://bing.com";
    public static final String TEST_USERNAME = "UdacityUser";
    public static final String TEST_PASSWORD = "UdacityPassword";
    @LocalServerPort
    private int port;
    private WebDriver webDriver;
    private WebDriverWait wait;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void beforeEach() {
        this.webDriver = new ChromeDriver();
        this.wait = new WebDriverWait(webDriver, 100);
    }

    @AfterEach
    public void afterEach() {
        if (this.webDriver != null) {
            webDriver.quit();
        }
    }

    // add cred
    @Test
    private void testAddCred() {
        signUpAndLogin("Jon", "Smith", "jsmith", "password");
        addNewCred(WEB_TEST, TEST_USERNAME, TEST_PASSWORD);
        webDriver.findElement(By.id("aResultSuccess")).click();
        webDriver.findElement(By.id("nav-credentials-tab")).click();
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 3);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rowCredentialUrl")));
        WebElement credentialUrlElement = webDriver.findElement(By.id("rowCredentialUrl"));
        WebElement credentialUsernameElement = webDriver.findElement(By.id("rowCredentialUsername"));
        WebElement credentialPasswordElement = webDriver.findElement(By.id("rowCredentialPassword"));
        assertEquals(WEB_TEST, credentialUrlElement.getText());
        assertEquals(TEST_USERNAME, credentialUsernameElement.getText());
        assertFalse(credentialPasswordElement.getText().isEmpty());
    }

    @Test
    public void testEditCred() throws InterruptedException {
        signUpAndLogin("Jon", "Smith", "jsmith", "password");
        Integer initCredListSize = getCredList().size();
        addNewCred(WEB_TEST, TEST_USERNAME, TEST_PASSWORD);
        webDriver.findElement(By.id("aResultSuccess")).click();
        webDriver.findElement(By.id("nav-credentials-tab")).click();
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 3);
        WebElement passField = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rowCredentialPassword")));
        String oldPW = passField.getText();
        editCredPassword("changedPW");
        webDriver.findElement(By.id("aResultSuccess")).click();
        webDriver.findElement(By.id("nav-credentials-tab")).click();
        assertEquals(initCredListSize, getCredList().size());
    }
    private void signUpAndLogin(String firstName, String lastName, String TEST_USERNAME, String TEST_PASSWORD) {
        SignupTest signUpPage = new SignupTest(webDriver, this.port);
        signUpPage.doMockSignUp(firstName, lastName, TEST_USERNAME, TEST_PASSWORD);
        LoginPage loginPage = new LoginPage(webDriver, this.port);
        loginPage.doLogIn(TEST_USERNAME, TEST_PASSWORD);
    }


    private void addNewCred(String WEB_TEST, String TEST_USERNAME, String TEST_PASSWORD) {
        webDriver.findElement(By.id("nav-credentials-tab")).click();

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 3);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNewCredential")));
        webDriver.findElement(By.id("btnAddNewCredential")).click();

        webDriverWait = new WebDriverWait(webDriver, 3);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));

        webDriver.findElement(By.id("credential-url")).sendKeys(WEB_TEST);
        webDriver.findElement(By.id("credential-username")).sendKeys(TEST_USERNAME);
        webDriver.findElement(By.id("credential-password")).sendKeys(TEST_PASSWORD);
        webDriver.findElement(By.id("btnSaveCredential")).click();

    }

    private void deleteCred() {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 3);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteCredentialLink")));
        webDriver.findElement(By.id("deleteCredentialLink")).click();
    }

    private void editCredPassword(String editPW) {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 3);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnEditCredential")));
        webDriver.findElement(By.id("btnEditCredential")).click();
        webDriverWait = new WebDriverWait(webDriver, 3);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password")));
        password.clear();
        password.sendKeys(editPW);
    }

    private List<Map<String, String>> getCredList() {
        List<Map<String, String>> credList = new ArrayList<>();
        WebElement table = webDriver.findElement(By.id("credentialTable"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements(By.tagName("td"));
            if (cols.size() == 3) {
                Map<String, String> cred = new HashMap<>();
                cred.put("url", cols.get(0).getText());
                cred.put("username", cols.get(1).getText());
                cred.put("password", cols.get(2).getText());
                credList.add(cred);
            }
        }
        return credList;
    }
}
