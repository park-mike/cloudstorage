package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {

    @LocalServerPort
    private int port;
    private WebDriver webDriver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.webDriver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    public void testHomePageAccessWithoutLogin() {
        webDriver.get("http://localhost:" + port + "/home");
        assertEquals("Login", webDriver.getTitle());
    }

    @Test
    public void testSignupAndLogout() {
        signUpAndLogin("Jon", "Doe", "jDoe", "password");
        assertEquals("Home", webDriver.getTitle());

        webDriver.findElement(By.id("logoutButton")).click();
        assertEquals("Login", webDriver.getTitle());

        webDriver.get("http://localhost:" + port + "/home");
        assertEquals("Login", webDriver.getTitle());
    }

    private void signUpAndLogin(String firstName, String lastName, String userName, String password) {

        SignupTest signUpPage = new SignupTest(webDriver, this.port);
        signUpPage.doMockSignUp(firstName, lastName, userName, password);

        LoginPage loginPage = new LoginPage(webDriver, this.port);
        loginPage.doLogIn(userName, password);

    }

}
