package com.javaacademy.Cinema;

import com.javaacademy.Cinema.dto.MovieDto;
import com.javaacademy.Cinema.dto.SessionDto;
import com.javaacademy.Cinema.entity.Movie;
import com.javaacademy.Cinema.entity.Session;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SessionControllerTest {
    private Movie movieTest;
    private MovieDto movieDtoTest;
    private Session sessionTest;
    private SessionDto sessionDtoTest;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;

        movieTest = Movie.builder()
                .id(1)
                .name("TestName")
                .description("Description movie in Test")
                .build();

        movieDtoTest = MovieDto.builder()
                .name(movieTest.getName())
                .description(movieTest.getDescription())
                .build();
        sessionDtoTest = SessionDto.builder()
                .datetime(LocalDateTime.of(2025,02,20,12, 0,0 ))
                .price(BigDecimal.valueOf(500))
                .idMovie(movieTest.getId())
                .build();
    }


    @Test
    public void createSessionSuccess() {

        RestAssured.given()
                .header("user-token", "secretadmin123")
                .contentType(ContentType.JSON)
                .body(sessionDtoTest)
                .when()
                .post("/session")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("datetime", equalTo(sessionDtoTest.getDatetime()))
                .body("price", equalTo(sessionDtoTest.getPrice()))
                .body("id_movie", equalTo(sessionDtoTest.getIdMovie()));

    }

    @Test
    public void createSessionTokenValueForbidden() {
        RestAssured.given()
                .header("user-token", "error")
                .contentType(ContentType.JSON)
                .body(sessionDtoTest)
                .when()
                .post("/session")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract();
    }

    @Test
    public void createSessionTokenNameForbidden() {

        RestAssured.given()
                .header("error-token", "secretadmin123")
                .contentType(ContentType.JSON)
                .body(sessionDtoTest)
                .when()
                .post("/session")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract();
    }

    @Test
    public void getSessionSuccess() {
        RestAssured.given()
                .get("/session")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract();

    }

    @Test
    public void getFreePlaceSessionSuccess() {
        RestAssured.given()
                .get("/session/1/free-place")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
