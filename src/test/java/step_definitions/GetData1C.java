package step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.notNullValue;

public class GetData1C {
    private RequestSpecification givenBuilder;
    private Response response;
    @Given("Создание предусловий с ключом {string} и названием ключа {string}")
    public void preconditions(String queryKey, String keyName){
        givenBuilder = given()
                .baseUri("http://172.18.1.7/UNIPRO-TEST-021/hs")
                .auth().basic("api", "!e7TcE@3zK_6UP$C1")
                .queryParam(keyName, queryKey);
    }
    @When("Отправка запроса на {string}")
    public void sendRequest(String URI){
        response = givenBuilder.when()
                .get(URI);
    }
    @Then("Проверить что значение в поле {string} соответствует {string}")
    public void checkResponse(String field, String expected){
        response.then()
                .statusCode(200)
                .body(field, hasKey(expected));
    }
    @Then("Проверить что значение в поле {string} не пустое и код 200")
    public void checkNotNullValue(String field){
        response.then()
                .statusCode(200)
                .body(field, notNullValue());
    }
}