import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Owner("Алина Суликова")
@Feature("Интеграция обмена данными с 1С, по движению данных и контингента")
@Epic("1С:Университет ПРОФ")
public class GetData1CTest {
    private final static Logger logger = LoggerFactory.getLogger(GetData1CTest.class);
    @BeforeAll
    public static void setUp(){
        baseURI = "http://172.18.1.7/UNIPRO-TEST-021/hs";
        authentication = basic("api", "!e7TcE@3zK_6UP$C1");
    }
    @ParameterizedTest
    @Tag("1C")
    @Tag("smoke")
    @DisplayName("Проверка получения справочников из 1С")
    @Description("""
            Проверяется получение справочников из 1С по параметру catalog
            
            Шаги:
            1. Передать в query-параметре catalog - значение справочника
            2. Выполнить GET-запрос /Coordinate_Catalogs/Catalogs
            
            Ожидаемый результат:
            1. Код ответа 200
            2. В ответе success = true
            3. В первом элементе массива data присутствует ожидаемое поле
            """)
    @CsvSource({"GROUPS, working_edu_plans", "SPECIALITIES, education_level_guid", "DISCIPLINES, is_school" })
    public void dictionaryTest(String queryKey, String field){
        logger.info("Запускается тест с параметрами: {} Проверяется: {}", queryKey, field);
        given()
                .queryParam("catalog", queryKey) //"SPECIALITIES" - "education_level_guid" ; "DISCIPLINES" - "is_school"
        .when()
                .get("/Coordinate_Catalogs/Catalogs")
        .then()
                .statusCode(200)
                .body("success", is(true))
                .body("data[0]", hasKey(field)); //проверка только 1 элемента массива даты
    }
    @ParameterizedTest
    @Tag("1C")
    @Tag("smoke")
    @DisplayName("Проверка получения структуры учебного плана из 1С")
    @Description("""
            Проверяется получение структуры учебного плана из 1С по GUID учебного плана
            
            Шаги:
            1. Передать в query-параметре guid - значение foreign_guid учебного плана из 1С
            2. Выполнить GET-запрос /Coordinate_Education/Plans_Structure
            
            Ожидаемый результат:
            1. Код ответа 200
            2. В ответе success = true
            3. В data.doc_number приходит данные УП
            """)
    @ValueSource(strings = {"e6e5eaaf-a348-11ef-a764-005056941fa2", "d79500cb-e2f9-11ee-b704-40a6b7970811", "828ec8ea-c911-11f0-a789-005056941fa2"})
    public void getEducationPlan(String guid){
        logger.info("Запускается тест с гуидом УП : {}", guid);
        given()
                .queryParam("guid", guid) //гуид учебного плана из 1С (foreign guid)
        .when()
                .get("/Coordinate_Education/Plans_Structure")
        .then()
                .statusCode(200)
                .body("success", is(true))
                .body("data.doc_number", notNullValue());
    }


}
//двойной ctrl - allure:serve для вывода отчета в аллюр
