import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AppCardDeliveryTest {
    @Test
    void shouldSubmitRequest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");

        int days = 5; // Adding 5 days
        String planningDate = generateDate(days);
        String selectAll = Keys.chord(Keys.COMMAND, "a");
        $("[data-test-id='date'] input").click();
        $("[data-test-id='date'] input").shouldBe(visible).sendKeys(selectAll);
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);

        $("[data-test-id='date'] input").sendKeys(planningDate);

        $("[data-test-id='name'] input").setValue("Марина Зинченко-Петрова");
        $("[data-test-id='phone'] input").setValue("+79111440000");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();

        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
