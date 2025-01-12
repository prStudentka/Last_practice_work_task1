package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;


import pages.MainPage;


public class TestSiteMts {
    protected static MainPage mainPage;

    @BeforeAll
    public static void setUp() {
        Configuration.browserSize = "1920x1080";
        mainPage = new MainPage();
    }
    
    @AfterAll
    public static void tearDown() {
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }
}