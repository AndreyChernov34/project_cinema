package com.javaacademy.Cinema;

import com.javaacademy.Cinema.controller.TicketController;
import com.javaacademy.Cinema.dto.TicketDto;
import com.javaacademy.Cinema.dto.TicketResponseDto;
import com.javaacademy.Cinema.service.TicketService;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TicketControllerTest {
    private TicketDto ticketDtoTest;
    private TicketController ticketController;
    @LocalServerPort
    private int port;

    @Mock
    private TicketService ticketService;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        ticketController = new TicketController(ticketService);
    }

    @Test
    public void GetAllPayTicketSuccessTest() {
    }

    @Test
    public void GetAllPayTicketValueForbiddenTest() {
        RestAssured.given()
                .header("user-token", "error")
                .when()
                .get("/ticket/saled")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void testGetAllPayTicketNameForbiddenTest() {
        RestAssured.given()
                .header("error", "secretadmin123")
                .when()
                .get("/ticket/saled")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void PayTicketSuccessTest() {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setPlaceName("A1");
        ticketDto.setSessionId(1);
        TicketResponseDto ticketResponseDto = TicketResponseDto.builder()
                .movieName("Терминатор")
                .placeNname("A1")
                .ticketId(1)
                .dateTime(LocalDateTime.of(2025, 2, 2, 12, 0))
                .build();

        when(ticketService.payTicket(any(TicketDto.class))).thenReturn(ticketResponseDto);

        RestAssured.given()
                .contentType("application/json")
                .body(ticketDto)
                .when()
                .post("/ticket/booking")
                .then()
                .statusCode(200);
    }
}
