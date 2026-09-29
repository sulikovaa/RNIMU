package step_definitions;

import dao.edu_plan.EducationPlanHiberImplement;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.basic;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

/**
 * 1. В методе source собираем foreign GUID учебных планов из БД
 * 2. Полученные foreign GUID передаем в запрос получения структуры УП из 1С
 * 3. Проверяем успешность ответа, отсутствие ошибки и соответствие ID учебного плана переданному foreign GUID
 **/

@Owner("Алина Суликова")
@Feature("Получение структуры учебного плана из 1С")
@Epic("Учебные планы")

public class GetEduPlanTest {
    @BeforeAll
    public static void setUp() {
        baseURI = "http://172.18.1.7/UNIPRO-TEST-021/hs";
        authentication = basic("api", "!e7TcE@3zK_6UP$C1");
    }

    @ParameterizedTest
    @Tag("1C")
    @DisplayName("Получение структуры учебного плана по foreign GUID")
    @Description("""
            Проверяется получение структуры учебного плана из 1С по foreign GUID учебного плана
            
            Шаги:
            1. Получить из БД список foreign GUID учебных планов
            2. Передать foreign GUID учебного плана в query-параметре guid
            3. Выполнить GET-запрос /Coordinate_Education/Plans_Structure
            4. Проверить успешность ответа
            5. Проверить, что в ответе отсутствует ошибка
            6. Проверить, что data.id в ответе соответствует переданному foreign GUID учебного плана
            
            Ожидаемый результат:
            1. Код ответа 200
            2. В ответе success = true
            3. В поле error возвращается null
            4. Значение data.id равно переданному foreign GUID учебного плана
            """)
    @MethodSource("getEduPlanGuids")
    public void getEduPlanCheckSuccess(String eduPlanForeignGuid) {
        given()
                .queryParam("guid", eduPlanForeignGuid)
                .when()
                .log().all()
                .get("/Coordinate_Education/Plans_Structure")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("error", nullValue())
                .body("data.id", equalTo(eduPlanForeignGuid));
    }
    public static List<String> getEduPlanGuids() {
        EducationPlanHiberImplement educationPlanHiberImplement = new EducationPlanHiberImplement();
        return educationPlanHiberImplement.getEducationPlanWithLimit(5);
    }
}