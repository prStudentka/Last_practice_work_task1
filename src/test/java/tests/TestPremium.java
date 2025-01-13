package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import pages.PremiumPackage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestPremium  extends TestSiteMts {

	private static PremiumPackage premium;
	
    @BeforeAll
    public static void openPagePremiumTest() throws Exception {
    	premium = mainPage.getPremiumPage();
    }
 
    @Order(2)
    @DisplayName("Негативный тест проверки заполнения поля года рождения")
    @ParameterizedTest(name= "Проверка валидации года рождения {1} {0}")
    @CsvSource({
    	"Гончарова Наталья Ивановна, 01.03.2007, +7 495 777-000-1, Москва",
    	"Пушкин Александр Сергеевич, 10.06.2027, +7 495 777-000-2, Орёл"
    })
    public void checkValidateAgeNegative(String fio, String birth, String phone, String city) throws Exception {
    	premium.checkAge(fio, birth, phone, city);
    }
    
    @Order(1)
    @DisplayName("Позитивный тест проверки заполнения поля года рождения")
    @ParameterizedTest(name= "Проверка валидации года рождения {1} {0}")
    @CsvSource({
    	"Лопухова Наталья Ивановна, 01.01.2007, +7 495 777-000-1, Москва",
    	"Сарай Александр Сергеевич, 10.06.1930, +7 495 777-000-2, Орёл",
    })
    public void checkValidateAge(String fio, String birth, String phone, String city) throws Exception {
    	premium.checkAge(fio, birth, phone, city);
    }

    @Order(3)
    @DisplayName("Позитивный тест проверки заполнения поля города")
    @ParameterizedTest(name= "{index}: Проверка валидации города-получателя {3} {0}")
    @CsvSource({
    	"Ложкина Наталья Ивановна, 01.02.2006, +7 495 777-000-1, Пермь",
    	"Сарай Александр Сергеевич, 10.06.1930, +7 495 777-000-2, Новосибирск"
    })
    public void checkValidateCity(String fio, String birth, String phone, String city) throws Exception {
    	premium.checkCity(fio, birth, phone, city);
    }
    
    @Order(4)
    @DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
    @DisplayName("Негативный тест проверки заполнения поля города")
    @ParameterizedTest(name= "Проверка валидации города-получателя ({0}, {4})")
    @CsvSource({
    	"Ввод цифр, Человек Наталья Ивановна, 01.03.2007, +7 495 777-000-1, 122222 ",
    	"Ввод спецсимволов, Электрон Александр Сергеевич, 10.06.1990, +7 495 777-000-2, @===---",
    	"Ввод иностранные буквы, Чижик Александра Сергоевишна, 10.06.2003, +7 495 777-000-2, Pariss",
    })
    public void checkValidateCityNegative(String description, String fio, String birth, String phone, String city) throws Exception {
    	premium.checkValidateCity(fio, birth, phone, city);
    }

    @Order(4)
    @DisplayName("Негативный тест проверки заполнения поля ФИО пустым")
    @Test
    public void checkValidateFIONegative() throws Exception {
        premium.checkValidateFIO("");
    }
}
