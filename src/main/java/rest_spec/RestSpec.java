package rest_spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestSpec {
    public static RequestSpecification getInfoByAPIkeySpec(){
        return new RequestSpecBuilder()
                .setBaseUri("https://api-gateway-test.rsmu.ru/coordinate-support/")
                .addHeader("CH-Api-Key", "iRK9o6Xwwef3FrwD7z83NkOO6cOawFI3nOkcU9ke/cqtLm1JpAASmFqTTkRRWTlx")
                .build();
    }

    public static RequestSpecification getInfoByAdminTokenSpec(){
        Response response = given()
                .formParam("username","sulikova_ae@rsmu.ru")
                .formParam("password", "Hardpass1!")
              //.formParam("_csrf" ,"QVRSci-_WUG5Tzob29c3jbgJ5uWX_WrBR10jeYmCILWv2zN3dmxhFh-LPHiUKgktvfoDtNtry9yvnFjsdTsUTbC6EICfvgEV")
        .when()
                .post("https://lk-auth-test.rsmu.ru/login");
        String cookieAuth = response.getCookie("sso_access_key");
        String cookieKey = response.getCookie("key");

        return new RequestSpecBuilder()
                .setBaseUri("https://api-gateway-test.rsmu.ru/coordinate-support/")
                .addHeader("Authorization", "Bearer "+ cookieAuth)
                .addCookie("Cookie","key="+cookieKey)
                .addHeader("content-type", "application/json")
                .build();
    }
}