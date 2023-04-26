package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class NoteTest {
    @LocalServerPort
    private int port;
    private WebDriver webDriver;
    private WebDriverWait wait;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.webDriver = new ChromeDriver();
        this.wait = new WebDriverWait(webDriver, 3);
    }

    @AfterEach
    public void afterEach() {
        if (this.webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    public void createNote() {

        signUpAndLogin("Joe", "Smith", "JoeSmith", "password");

        addNewNote("Note Test", "Test note.");

        webDriver.findElement(By.id("aResultSuccess")).click();

        webDriver.findElement(By.id("nav-notes-tab")).click();

        Assertions.assertEquals("Test note", getFirstNote().getNoteTitle());
    }

    @Test
    public void editNote() {

        signUpAndLogin("Joe", "Sally", "JoeSally", "password");

        addNewNote("Note Test", "Test note.");

        webDriver.findElement(By.id("aResultSuccess")).click();

        editNoteTitle("Test Note");

        webDriver.findElement(By.id("aResultSuccess")).click();

        webDriver.findElement(By.id("nav-notes-tab")).click();

        Assertions.assertEquals("Updated test note", getFirstNote().getNoteTitle());
    }

    @Test
    public void deleteNote() {
        signUpAndLogin("Tim", "Smith", "tsmith", "password");
        addNewNote("Test note", "Test note.");
        webDriver.findElement(By.id("aResultSuccess")).click();
        webDriver.findElement(By.id("nav-notes-tab")).click();
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 3);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteNoteLink")));
        webDriver.findElement(By.id("deleteNoteLink")).click();
        webDriver.findElement(By.id("aResultSuccess")).click();
        webDriver.findElement(By.id("nav-notes-tab")).click();
        Assertions.assertTrue(isNoteListEmpty());
    }

    private void signUpAndLogin(String firstName, String lastName, String userName, String password) {
        SignupTest signUpPage = new SignupTest(webDriver, this.port);
        signUpPage.doMockSignUp(firstName, lastName, userName, password);

        LoginPage loginPage = new LoginPage(webDriver, this.port);
        loginPage.doLogIn(userName, password);

    }

    private void addNewNote(String noteTitle, String noteDescription) {

        webDriver.findElement(By.id("nav-notes-tab")).click();

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 3);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddNewNote")));
        webDriver.findElement(By.id("btnAddNewNote")).click();

        webDriverWait = new WebDriverWait(webDriver, 3);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        webDriver.findElement(By.id("note-title")).sendKeys("Test note");
        webDriver.findElement(By.id("note-description")).sendKeys("This is a test note.");

        webDriver.findElement(By.id("btnSaveNote")).click();
    }

    private void editNoteTitle(String newNoteTitle) {

        webDriver.findElement(By.id("nav-notes-tab")).click();

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 3);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnEditNote")));
        webDriver.findElement(By.id("btnEditNote")).click();
        webDriverWait = new WebDriverWait(webDriver, 3);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitleField = wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
        noteTitleField.clear();
        noteTitleField.sendKeys(newNoteTitle);

        webDriver.findElement(By.id("btnSaveNote")).click();
    }

    private boolean isNoteListEmpty() {
        List<WebElement> noteTitles = webDriver.findElements(By.id("rowNoteTitle"));
        List<WebElement> noteDescriptions = webDriver.findElements(By.id("rowNoteDescription"));
        return noteDescriptions.isEmpty() && noteTitles.isEmpty();
    }

    private Note getFirstNote() {

        WebElement titleElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("rowNoteTitle")));
        WebElement descriptionElement = webDriver.findElement(By.id("rowNoteDescription"));
        String title = titleElement.getText();

        String description = descriptionElement.getText();
        return new Note(title, description);
    }

}
