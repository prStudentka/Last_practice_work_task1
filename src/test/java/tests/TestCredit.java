package tests;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

import pages.CreditAll;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCredit extends TestSiteMts{
    private static CreditAll creditCalc;
    
    @BeforeAll
    public static void openPageCreditTest() throws Exception {
        creditCalc = mainPage.getCreditsPage();
    }

	@Test
    @Order(1)
    @DisplayName("Проверка отображения суммы по клику на значение")
    public void checkButtonsSumTest() throws Exception {
        creditCalc.checkMin();
        creditCalc.checkMax();
        creditCalc.checkSumPledge();
    }

    @Test
    @Order(2)
    @DisplayName("Проверка отображения суммы по слайдеру")
    public void checkSliderTest() throws Exception {
    	creditCalc.checkSlider();
    }
    
    @Test
    @Order(3)
    @Disabled
    @DisplayName("Проверка отображения суммы при нажатии кнопки и движения слайдера")
    public void checkButtonSumAndSliderTest() throws Exception {
    	creditCalc.checkButtonSumPledgeAfterSlider();
    }
    
    @Test
    @Order(4)
    @DisplayName("Проверка на выброс исключения провального теста checkButtonSumAndSliderTest")
    public void fixMeCheckButtonSumAndSliderFail() {
        assertThrows(AssertionError.class, this::checkButtonSumAndSliderTest);
    }
    
    @Order(5)
    @DisplayName("Позитивный тест проверки кнопок сумма и годы")
    @ParameterizedTest(name= "Проверка активности кнопок {0} с суммой {2}")
    @CsvSource({
    	"7 лет, 7, 5 000 001",
    	"15 лет, 15, 15 000 000"
    })
    public void checkModalWindowByAge(String description, String argument1, String argument2) throws Exception {
    	creditCalc.checkYearsMoreThanFive(argument1, argument2);
    }
    
   
    @Order(6)
    @DisplayName("Негативный тест проверки кнопок сумма и годы")
    @ParameterizedTest(name= "Проверка кнопки {0} лет с суммой {1} на неактивность ")
    @CsvSource({
    	"7, 5 000 000",
    	"10, 20 000"
    })
    public void checkModalWindowByAgeNegative(String argument1, String argument2) throws Exception {
    	creditCalc.checkYearsMoreThanFive(argument1, argument2);
    }
}
