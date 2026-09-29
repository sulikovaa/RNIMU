package step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static rest_spec.RestSpec.getInfoByAPIkeySpec;

public class GetDataWPD {
    private RequestSpecification givenBuilder;
    private Response response;
    @Given("Получение данных РПД по guid {string}")
    public void preconditions(String pathParam){
        givenBuilder = given()
                .spec(getInfoByAPIkeySpec())
                .pathParam("guid",pathParam);
    }
    @When("Отправка запроса 2 на {string}")
    public void sendRequest(String endpoint){
            response= givenBuilder.when()
                    .get(endpoint);
        }
    @Then("Проверить что поле {string} РПД и параметр {string} совпадают и код 200")
    public void checkGuidMatch(String key, String expectedValue){
        response.then()
                .statusCode(200)
                .body(key ,equalTo(expectedValue));
    }
}
