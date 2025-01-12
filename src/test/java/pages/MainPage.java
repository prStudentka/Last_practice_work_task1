package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import core.Config;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class MainPage {

    private static final SelenideElement MENU_CREDITS = $x("//a[@href='/chastnim-licam/krediti/']");
    private static final SelenideElement MENU_PREMIUM = $x("//a[@href='/chastnim-licam/private-banking/premium-package/']");
    private static final SelenideElement SUBMENU_CREDIT = $x("//a[@href='" + Config.CALCULATOR_URL + "']");
    private static final String CREDITS_CARD = "Кредиты";
    private static final SelenideElement SUBMENU_PREMIUM = $x("//a[@href='" + Config.PREMIUM_URL + "']");

    public MainPage() {
        Allure.step("Открываем главную страницу mtsbanka");
        Selenide.open(Config.BASE_URL);
    }

    @Step("Открываем страницу Кредит наличными")
    public CreditAll getCreditsPage() throws Exception {
        SelenideElement card = $(MENU_CREDITS).shouldBe(text(CREDITS_CARD));
        actions().moveToElement(card).build().perform();
        if (card.exists()) {
            actions().moveToElement($(SUBMENU_CREDIT)).click().build().perform();
            return new CreditAll();
        }
        return null;
    }
    
    @Step("Открываем страницу Премиальное обслуживание")
    public PremiumPackage getPremiumPage() throws Exception {
        actions().moveToElement(MENU_PREMIUM).build().perform();
        actions().moveToElement($(SUBMENU_PREMIUM)).click().build().perform();
        return new PremiumPackage();
    }
}
