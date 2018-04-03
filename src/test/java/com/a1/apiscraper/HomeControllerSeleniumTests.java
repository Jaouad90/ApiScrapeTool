package com.a1.apiscraper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiscraperApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class HomeControllerSeleniumTests {

    @LocalServerPort
    protected int serverPort;

    private static ChromeDriver browser;

    @Before
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver",
                "src/test/java/com/a1/apiscraper/resources/chromedriver.exe");
        browser = new ChromeDriver();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void closeBrowser() {
        browser.quit();
    }

    @Test
    public void succeedingAuthorizeRequest() {
        String baseUrl = "http://localhost:" + serverPort + "/api/add";
        browser.get(baseUrl);
        assertTrue(browser.getTitle().contains("Login"));
    }

    @Test
    public void failingAuthentication() {
        String baseUrl = "http://localhost:" + serverPort + "/";
        browser.get(baseUrl);

        WebElement usernameInput =  browser.findElementById("username");
        usernameInput.sendKeys("test-user");
        WebElement passwordInput =  browser.findElementById("password");
        passwordInput.sendKeys("123456");
        WebElement loginButton = browser.findElementById("loginButton");

        loginButton.click();

        WebDriverWait wait = new WebDriverWait(browser, 10);
        WebElement messageElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("loginError"))
        );

        String message = messageElement.getText();
        String successMsg = "Invalid username and password.";

        assertEquals(message, successMsg);
        assertTrue(browser.getTitle().contains("Login"));
    }

    @Test
    public void succeedingAuthentication() {
        String baseUrl = "http://localhost:" + serverPort + "/";
        browser.get(baseUrl);

        WebElement usernameInput =  browser.findElementById("username");
        usernameInput.sendKeys("Admin");
        WebElement passwordInput =  browser.findElementById("password");
        passwordInput.sendKeys("Root");
        WebElement loginButton = browser.findElementById("loginButton");

        loginButton.click();

        WebDriverWait wait = new WebDriverWait(browser, 15);
        wait.until(ExpectedConditions.titleContains("Dashboard"));

        assertTrue(browser.getTitle().contains("Dashboard"));
    }
}
