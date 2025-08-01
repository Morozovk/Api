package api.tests;

import api.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static api.spec.BaseSpec.baseResponseSpec;
import static api.spec.BaseSpec.requestBaseSpec;
import static api.tests.TestData.apiKey;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@Tag("Users")
@DisplayName("Проверка страниц с юзерами")
public class Users extends TestBase {

    @DisplayName("Отображение страницы с юзерами")
    @Test
    void listUsersTest() {

        ListUserBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                        .header("x-api-key", apiKey)

                        .when()
                        .queryParam("page", "2")
                        .get("/users")

                        .then()
                        .spec(baseResponseSpec(200))
                        .extract().as(ListUserBodyModel.class));

        step("Проверяем ответ", () -> {
            assertNotNull(response.getPage());
            assertNotNull(response.getData());

            UserBodyModel firstUser = response.getData().get(0);
            assertNotNull(firstUser.getId());
            assertTrue(firstUser.getEmail().endsWith("@reqres.in"), "Email должен оканчиваться на @reqres.in");
            assertNotNull(firstUser.getFirst_name());
            assertNotNull(firstUser.getLast_name());
            assertTrue(firstUser.getAvatar().endsWith(firstUser.getId() + "-image.jpg"),
                    "Изображениедолжно иметь название -image и быть в формате jpg");
        });
    }

    @Tag("Users")
    @DisplayName("Отображение страницы с юзерами")
    @Test
    void listNotFoundSingleUserTest() {

        ListUserBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                        .header("x-api-key", apiKey)

                        .when()
                        .queryParam("page", "2")
                        .get("/users")

                        .then()
                        .spec(baseResponseSpec(200))
                        .extract().as(ListUserBodyModel.class));

        step("Проверяем ответ", () -> {
            assertNotNull(response.getPage());
            assertNotNull(response.getData());

            UserBodyModel firstUser = response.getData().get(0);
            assertNotNull(firstUser.getId());
            assertTrue(firstUser.getEmail().endsWith("@reqres.in"), "Email должен оканчиваться на @reqres.in");
            assertNotNull(firstUser.getFirst_name());
            assertNotNull(firstUser.getLast_name());
            assertTrue(firstUser.getAvatar().endsWith(firstUser.getId() + "-image.jpg"),
                    "Изображениедолжно иметь название -image и быть в формате jpg");
        });
    }

    @Tag("Users")
    @DisplayName("Отображение страницы с юзером")
    @Test
    void listSingleUserTest() {

        UserDataBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                        .header("x-api-key", apiKey)

                        .when()
                        .get("/users/2")

                        .then()
                        .spec(baseResponseSpec(200))
                        .extract().as(UserDataBodyModel.class));

        step("Проверяем ответ", () -> {
            UserBodyModel userData = response.getData();
            assertNotNull(userData.getId());
            assertTrue(userData.getEmail().endsWith("@reqres.in"),
                    "Email должен оканчиваться на @reqres.in");
            assertNotNull(userData.getFirst_name());
            assertNotNull(userData.getLast_name());
            assertTrue(userData.getAvatar().endsWith(userData.getId() + "-image.jpg"),
                    "Изображение должно иметь название -image и быть в формате jpg");
        });
    }
}
