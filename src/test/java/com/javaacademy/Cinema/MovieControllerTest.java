package com.javaacademy.Cinema;

import com.javaacademy.Cinema.dto.MovieDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MovieControllerTest {
    private MovieDto movieDtoTest;
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        log.info("port " + port);
        movieDtoTest = MovieDto.builder()
                .name("TestName1")
                .description("Description movie in Test1")
                .build();
    }

    @Test
    public void createMovieSuccess() {

        RestAssured.given()
                .header("user-token", "secretadmin123")
                .contentType(ContentType.JSON)
                .body(movieDtoTest)
                .when()
                .post("/movie")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("name", equalTo(movieDtoTest.getName()))
                .body("description", equalTo(movieDtoTest.getDescription()));
    }

    @Test
    public void createMovieTokenValueForbidden() {
        RestAssured.given()
                .header("user-token", "error")
                .contentType(ContentType.JSON)
                .body(movieDtoTest)
                .when()
                .post("/movie")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void createMovieTokenNameForbidden() {
        RestAssured.given()
                .header("error-token", "secretadmin123")
                .contentType(ContentType.JSON)
                .body(movieDtoTest)
                .when()
                .post("/movie")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void getMovieSuccess() {
        RestAssured.given()
                .get("/movie")
                .then()
                .statusCode(HttpStatus.OK.value());

    }


}
