package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;


import pages.MainPage;


public class TestSiteMts {
    protected static MainPage mainPage;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        System.setProperty("chromeoptions.args", "\"--no-sandbox\",\"--disable-dev-shm-usage\"");
        mainPage = new MainPage();
    }
    
    @AfterAll
    public static void tearDown() {
        Configuration.browser = "chrome";
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }
}
