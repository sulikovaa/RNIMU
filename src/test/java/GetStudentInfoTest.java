import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static rest_spec.RestSpec.*;

@Owner("Алина Суликова")
@Feature("Получение данных контингента")
@Epic("Кнтингент")

public class GetStudentInfoTest {
   @Test
   @Tag("Студенты")
   @Tag("smoke")
   @DisplayName("Получение информации о студенте по guid зачётной книжки")
   @Description("""
           Проверка получения информации о студенте по параметру record_book_guid
           
           Шаги:
           1. Выполнить GET-запрос /students с query-параметром record_book_guid

           Ожидаемый результат:
           1. Статус код 200
           2. Ключ data.status=ACTIVE
           """)
   public void getStudentInfoByRecordBookGuidSaStatusCheck(){
       given()
               .spec(getInfoByAdminTokenSpec())
               .queryParam("record_book_guid","68bb0ffc-5264-11ef-a75e-005056941fa2")
       .when()
               .log().all()
               .get("/students")
       .then()
               .log().all()
               .statusCode(200)
               .body("data[0].status", equalTo("ACTIVE"));
   }
    @Test
    @Tag("Студенты")
    @Tag("smoke")
    @DisplayName("Получение зачетных книжек студента, по person_guid")
    @Description("""
           Получение списка ЗК студента, по переданному гуиду студента //coordinate_db.student.e_guid
           
           Шаги:
           1. Выполнить GET-запрос /internal/students/record-books с query-параметром person_guid

           Ожидаемый результат:
           1. Статус код 200
           2. В массиве возвращается список зачетных книг студента и информация об образовательном процессе
           """)
   public void getRecordBooksInfo(){
       given()
               .spec(getInfoByAPIkeySpec())
               .queryParam("person_guid", "8c6494cc-48c9-11ef-a75e-005056941fa2") //прокинуть гуид студента
       .when()
               .get("internal/students/record-books")
       .then()
               .log().all()
               .statusCode(200);
   }
}
