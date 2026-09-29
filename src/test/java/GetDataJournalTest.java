import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static rest_spec.RestSpec.getInfoByAPIkeySpec;

@Owner("Алина Суликова")
@Feature("Получение данных журналов из ЛК")
@Epic("ЛК журналы")
public class GetDataJournalTest {
    @ParameterizedTest
    @Tag("Журналы")
    @Tag("smoke")
    @DisplayName("Получение данных журнала студента и проверка признака БРС")
    @Description("""
            Проверяется получение данных журнала студента ЛК
            
            Шаги:
            1. Сформировать тело запроса с lesson_id, person_guid, join_journal, student_page и student_rows
            2. Выполнить POST-запрос internal/journal

            Ожидаемый результат:
            1. Код ответа 200
            2. В теле ответа присутствует значение признака brs
            """) //булево brs возвращает true/false  зависимости от того, к какой РПД привязан журнал(с БРС или без)
    @CsvSource({"354034, 3fa85f64-5717-4562-b3fc-2c963f66afa6, false"})
    public void getStudentJournalThenCheckBrsShouldBeTrue(int lesson_id, String personGuid, boolean joinJournal){
        /*
        Варианты как передать боди:
        1. создать класс и в него передать значения (settings и в классе передаются значения)
        2. создать map и передаем значения в боди
        3. передать json боди стрингой
        4. передать json в файле а не стринге
        5. передать через form data в given
        */
        Map<String, Object> requestBody = Map.of("lesson_id",lesson_id,"person_guid",personGuid,"join_journal",joinJournal,"student_page",0,"student_rows",30);

        given()
                .spec(getInfoByAPIkeySpec())
                .body(requestBody)
                .contentType("application/json")
        .when()
                .log().all()
                .post("internal/journal")
        .then()
                .log().all()
                .statusCode(200)
                .body("data.brs", notNullValue());
    }
}