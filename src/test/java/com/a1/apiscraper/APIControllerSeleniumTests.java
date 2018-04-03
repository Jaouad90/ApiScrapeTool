package com.a1.apiscraper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiscraperApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class APIControllerSeleniumTests {

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
    public void failingAddAPIWithoutBaseURL() {
        String baseUrl = "http://localhost:" + serverPort + "/api/add";
        browser.get(baseUrl);
        assertTrue(browser.getTitle().contains("Add"));
    }
}
