package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.fail;

import org.openqa.selenium.Keys;

public class CreditAll {
	
    private static final SelenideElement MIN_SUM = $x("//div[@class='rc-slider-mark']/*[contains(text(), '20')]");
    private static final SelenideElement INPUT_SUM = $x("//input[@data-testid='input-slider']");
    private static final SelenideElement MAX_SUM = $x("//div[@class='rc-slider-mark']/*[contains(text(), '15')]");
    private static final SelenideElement PLEDGE_SUM = $x("//div[@class='rc-slider-mark']/*[contains(text(), '5')]");
    private static final SelenideElement SLIDER_HANDLER = $x("//div[@class='rc-slider-handle']");
    private String button_age = "//span[@id='age']";
    private static final SelenideElement MODAL_YEARS = $x("//div[@data-testid='text']");
    private final String EXPECTED_MODAL_TEXT = "Кредит на 7, 10 и 15 лет возможно взять только на сумму от 5 млн ₽ и только под залог";
    private final String[][] money = {{"тыс", "000"}, {"млн", "000 000"}};
    private final String EXPECTED_PLEDGE_SUM = "5 000 000 ₽";
    private final String FAIL_MESSAGE_PLEDGE_SUM = "sum should be " + EXPECTED_PLEDGE_SUM;
    
    
   @Step("Проверка кнопки минимальной суммы")
   public void checkMin() {
       String digits = $(MIN_SUM).innerText().replace(money[0][0], money[0][1]);
       $(MIN_SUM).click();
       $(INPUT_SUM).shouldBe(value(digits));
   }
   
   @Step("Проверка кнопки максимальной суммы")
   public void checkMax() {
        String digits = $(MAX_SUM).innerText().replace(money[1][0], money[1][1]);
        $(MAX_SUM).click();
        $(INPUT_SUM).shouldBe(value(digits));
    }

   @Step("Проверка кнопки суммы с залогом")
   public void checkSumPledge() {
        String digits = $(PLEDGE_SUM).innerText()
                .replace(money[1][0], money[1][1])
                .replace("от", "")
                .split("-")[0]
                .strip();
        $(PLEDGE_SUM).click();
        $(INPUT_SUM).shouldBe(value(digits));
    }

   @Step("Проверка слайдера суммы")
    public void checkSlider() throws Exception {
    	String beforeDigits = $(INPUT_SUM).val();
        actions().dragAndDropBy($(SLIDER_HANDLER), 1, 0).build().perform();
        $(INPUT_SUM).shouldNotBe(value(beforeDigits));
        $(INPUT_SUM).shouldBe(value("5 050 826 ₽"));
                
    }
   
   @Step("Проверка кнопки суммы с залогом после движения слайдера суммы")
   public void checkButtonSumPledgeAfterSlider() throws Exception {
	   $(INPUT_SUM).click();
       actions().dragAndDropBy($(SLIDER_HANDLER), 1, 0).build().perform();
       $(INPUT_SUM).shouldBe(value("5 101 952 ₽"));
       $(PLEDGE_SUM).click();
       if (!$(INPUT_SUM).val().equals(EXPECTED_PLEDGE_SUM)) {
    	   fail(FAIL_MESSAGE_PLEDGE_SUM); 
       }             
   }
 
   @Step("Проверка кнопок 7, 10 и 15 лет")
   public void checkYearsMoreThanFive(String age, String sum) throws Exception {
	   $(INPUT_SUM).sendKeys(Keys.CONTROL + "A");
	   $(INPUT_SUM).sendKeys(Keys.BACK_SPACE);
	   $(INPUT_SUM).append(sum);
	   SelenideElement button = $x(button_age.replace("age", age));
	   if (!button.isEnabled()) {
		   button.click();
		   $(MODAL_YEARS).shouldHave(Condition.exactText(EXPECTED_MODAL_TEXT));
	   }
   }

}
