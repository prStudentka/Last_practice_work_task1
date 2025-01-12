package pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.actions;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.Keys;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;

public class PremiumPackage {
	
	private static final SelenideElement FIO_FIELD = $x("//textarea[@name='clientFio']");
	private static final SelenideElement BIRTHDAY_FIELD = $x("//input[@name='birthDate']");
	private static final SelenideElement PHONE_FIELD = $x("//input[@name='phoneNumber']");
	private static final SelenideElement CITY_FIELD = $x("//input[@name='deliveryCity']");
	private static final SelenideElement VALIDATOR_AGE = $x("//div[contains(@class, 'DatePickerWrapper')]/following-sibling::div[@data-testid='text']");
	private static final String EXPECTED_VALIDATOR_AGE_MESSAGE = "Возраст клиента должен быть не менее 18 лет";
	private static final String EXPECTED_VALIDATOR_CITY_MESSAGE = "B данный город не выполняется доставка карты";
	private static final SelenideElement CITY_LISTBOX = $x("//li[contains(@data-suggestion-index, '0')]");
	private static final SelenideElement VALIDATOR_CITY = $x("//div[contains(@class, 'Wrapper-sc-10uh1mr-0')]/following-sibling::div[@data-testid='text']");
	private static final SelenideElement BUTTON_SUBMIT = $x("//button[@data-testid='button']");
	
    @Step("Проверка на совершеннолетие")
	public void checkAge(String fio, String birth, String phone, String city) {
       $(BIRTHDAY_FIELD).clear();
       $(BIRTHDAY_FIELD).setValue(birth);
       $(BIRTHDAY_FIELD).equals(birth);
       $(FIO_FIELD).sendKeys(Keys.CONTROL + "A");
 	   $(FIO_FIELD).sendKeys(Keys.BACK_SPACE);
       $(FIO_FIELD).sendKeys(fio);

       $(PHONE_FIELD).val(phone).click();
       if ($(VALIDATOR_AGE).exists()) {
    	   $(VALIDATOR_AGE).shouldHave(Condition.text(EXPECTED_VALIDATOR_AGE_MESSAGE));
       }  
	}
    
    @Step("Проверка на город-получатель карты")
	public void checkCity(String fio, String birth, String phone, String city) {
 	   $(FIO_FIELD).sendKeys(Keys.CONTROL + "A");
 	   $(FIO_FIELD).sendKeys(Keys.BACK_SPACE);
       $(FIO_FIELD).sendKeys(fio);
       $(CITY_FIELD).sendKeys(Keys.CONTROL + "A");
       $(CITY_FIELD).sendKeys(Keys.BACK_SPACE);
       $(CITY_FIELD).sendKeys(city);
       actions().moveToElement($(CITY_LISTBOX)).build().perform();
       actions().moveToElement($(CITY_LISTBOX)).click().build().perform();
       $(BIRTHDAY_FIELD).val(birth);
       $(PHONE_FIELD).val(phone).click();
       $(CITY_FIELD).shouldHave(Condition.value(city));
	}
    
    @Step("Проверка валидации города-получателя карты")
	public void checkValidateCity(String fio, String birth, String phone, String city) {
 	   $(FIO_FIELD).sendKeys(Keys.CONTROL + "A");
 	   $(FIO_FIELD).sendKeys(Keys.BACK_SPACE);
       $(FIO_FIELD).sendKeys(fio);
       $(BIRTHDAY_FIELD).setValue(birth);
       $(PHONE_FIELD).val(phone).click();
       $(CITY_FIELD).click();
       $(CITY_FIELD).sendKeys(Keys.CONTROL + "A");
       $(CITY_FIELD).sendKeys(Keys.BACK_SPACE);
       $(CITY_FIELD).val(city).hover();
       actions().moveToElement($(CITY_FIELD)).sendKeys(Keys.BACK_SPACE).build().perform();
       $(VALIDATOR_CITY).shouldHave(Condition.text(EXPECTED_VALIDATOR_CITY_MESSAGE));
	}
}
