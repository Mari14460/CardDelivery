import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AppCardDeliveryTest {
    @Test
    void shouldSubmitRequest() {
        open("http://localhost:9999");
        SelenideElement form = $("[id='root']");
        form.$("[data-test-id='city'] input").setValue("Санкт-Петербург");

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Using today's date
        c.add(Calendar.DATE, 5); // Adding 5 days
        String output = sdf.format(c.getTime());
        form.$("[data-test-id='date'] input").click();
        String selectAll = Keys.chord(Keys.COMMAND, "a");
        form.$("[data-test-id='date'] input").sendKeys(selectAll);
        form.$("[data-test-id='date'] input").sendKeys(output);
        form.$("[data-test-id='name'] input").setValue("Марина Зинченко-Петрова");
        form.$("[data-test-id='phone'] input").setValue("+79111440000");
        form.$("[data-test-id='agreement']").click();
        form.$(byText("Забронировать")).click();
        SelenideElement popup = $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        popup.$(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
    }
}
