import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static rest_spec.RestSpec.getInfoByAdminTokenSpec;

@Owner("Алина Суликова")
@Feature("Получение списка сотрудников")
@Epic("Сотрудники и РОП")
public class GetEmployeesListTest {
    @Test
    @Tag("Сотрудники")
    @Tag("smoke")
    @DisplayName("Получение данных по сотрудникам и РОП")
    @Description("""
            Проверка получения списка сотрудников, на странице Сотрудники и РОП
            Шаги:
            Ожидаемый результат:
            """)
    public void getEmployeeList() {
        given()
                .spec(getInfoByAdminTokenSpec())
        .when()
                .log().all()
                .get("wpd/employees?page={page}&rows={rows}", 0, 10)
        .then()
                .log().all()
                .statusCode(200)
                .body("data", is(notNullValue()));
    }
}