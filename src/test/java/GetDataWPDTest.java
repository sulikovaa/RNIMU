import dao.edu_plan.EducationPlanImplement;
import dao.wpd.WorkingProgramOfDisciplineImplement;
import entity.EduPlanItem;
import entity.WPDCriterias;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static java.util.Map.entry;
import static org.hamcrest.Matchers.*;
import static rest_spec.RestSpec.getInfoByAPIkeySpec;

@Owner("Алина Суликова")
@Feature("Получение данных РПД по идентификатору")
@Epic("РПД")
public class GetDataWPDTest {
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(GetDataWPDTest.class);
    private final EducationPlanImplement epi = new EducationPlanImplement();
    @ParameterizedTest
    @Tag("РПД")
    @Tag("smoke")
    @DisplayName("Получение данных РПД по guid")
    @Description("""
            Проверка получения данных РПД по guid
            
            Шаги:
            1. Передать guid РПД в path-параметр запроса
            2. Выполнить GET-запрос internal/wpd/{guid}
            3. Проверить, что ID РПД в ответе соответствует переданному guid
            5. Проверить признак БРС
            
            Ожидаемый результат:
            1. Код ответа 200
            2. ID РПД соответствует переданному guid
            3. Признак БРС = false
            """) //булево значение БРС зависит от РПД
    @MethodSource("getListWPDCriterias")
    public void getWPDInfoByGuid(WPDCriterias wpdCriterias){
        EduPlanItem eduPlanByGuid = epi.getEduPlanByGuid(wpdCriterias.getEduPlanId());
        logger.info("Запускется тест получения РПД по гуид: {} ", wpdCriterias.geteGuid() );
        given()
                .pathParam("guid",wpdCriterias.geteGuid())
                .spec(getInfoByAPIkeySpec())
        .when()
                .get("internal/wpd/{guid}")
        .then()
                .statusCode(200)
                .body("data.id", describedAs("ID РПД и guid не совпадают",equalTo(wpdCriterias.geteGuid()))) //выводится только в случае ошибки
                .body("data.brs", is(true)) //признак БРС
                .body("data.edu_plan_year_start", equalTo(eduPlanByGuid.yearStart()))
                .body("data.edu_plan_year_end", equalTo(eduPlanByGuid.yearEnd()));
    }
    @ParameterizedTest
    @Tag("РПД")
    @Tag("smoke")
    @DisplayName("Получение данных РПД и создание протокола рассмотрения в полученной РПД")
    @Description("""
            Получения данных РПД по guid, и создание протокола рассмотрения с привязкой к полученной РПД
            
            Шаги:
            1. Передать guid РПД в path-параметр запроса
            2. Выполнить GET-запрос internal/wpd/{guid}
            3. Получить base_wpd_id из ответа
            4. Выполнить GET-запрос internal/wpd/protocol с полученным query-параметром wpd_id
            5. Выполнить POST-запрос internal/wpd/protocol с данными протокола
            
            Ожидаемый результат:
            1. Запрос РПД возвращает код 200
            2. Запрос протокола возвращает код 200 и success = true
            3. Создание/обновление протокола возвращает код 200. Сохраненные данные протоколов сохраняются в Координату в wpd_rewiew_protocol
            """)
    //@ValueSource(strings = {"0b12a130-1671-43a2-af3d-2d0d963ac9a6"})
    @MethodSource("getListWPDCriterias")
    public void getWEPInfoByGuid(WPDCriterias wpdCriterias){
        logger.info("Проверка получения информации о РУП через РПД guid: {}", wpdCriterias.geteGuid());
        String response = given()
                .spec(getInfoByAPIkeySpec())
                .pathParam("guid", wpdCriterias.geteGuid())
        .when()
                .get("internal/wpd/{guid}")
                .asString();
        Object baseWPDId = JsonPath.from(response).get("data.base_wpd_id");
        Object cathedraTitle = JsonPath.from(response).get("data.cathedras");
        Object cathedraGuid = JsonPath.from(response).get("data.semesters[0].cathedras[0].id");

        given()
                .spec(getInfoByAPIkeySpec())
                .queryParam("wpd_id", baseWPDId)
        .when()
                .get("internal/wpd/protocol")
        .then()
                .statusCode(200)
                .body("success", is(true));
        given()
                .spec(getInfoByAPIkeySpec())
                .contentType("application/json")
                .queryParam("wpd_id", baseWPDId)
                .body(java.util.List.of(
                        Map.ofEntries(
                                entry("date", "2026-06-03"),
                                entry("number", "тест авто"),
                                entry("cathedra_guid", "cc5415b4-4362-11ef-8179-005056bb5e35"),
                                entry("cathedra_title", "Кафедра факультетской хирургии № 1 ИХ"),
                                entry("department_title", "")
                        )
                ))
        .when()
                .log().all()
                .post("internal/wpd/protocol")
        .then()
                .log().all()
                .statusCode(200);
    }
    @ParameterizedTest
    @Tag("РПД")
    @Tag("smoke")
    @DisplayName("Получение семестров РПД по гуиду РПД")
    @Description("""
            Получения семестров привязанных к РПД, по guid
            
            Шаги:
            1.Выполнить GET-запрос internal/wpd/semesters с query-параметром wpd_id
            
            Ожидаемый результат:
            1. Код ответа 200
            2. В теле ответа возвращается УИД семестра и его номер
            """)
    @MethodSource("getListWPDCriterias")
    public void getWEPSemestersByGuid(WPDCriterias wpdCriterias){
        logger.info("Проверка получения семестров РПД с guid: {}", wpdCriterias.geteGuid());
        given()
                .queryParam("wpd_id", wpdCriterias.geteGuid())
                .spec(getInfoByAPIkeySpec())
        .when()
                .get("internal/wpd/semesters")
        .then()
                .log().all()
                .statusCode(200)
                .body("data.id", notNullValue())
                .body("data.semester_number", notNullValue());

    }
     static List<WPDCriterias> getListWPDCriterias(){
        WorkingProgramOfDisciplineImplement dataConnect = new WorkingProgramOfDisciplineImplement();
        return dataConnect.getWPDWithLimit(5);
    }
}
//двойной ctrl - allure:serve для вывода в аллюр"
//посмотреть какие параметры проверять про получении рпд в тестах
//на 106 строе прописать динамические значения , попробовать получить данные из другой таблицы 