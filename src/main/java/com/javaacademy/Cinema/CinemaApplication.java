package com.javaacademy.Cinema;

import lombok.Cleanup;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
public class CinemaApplication {

	// @SneakyThrows
	public static void main(String[] args) {
//		Class.forName("org.postgresql.Driver");
//		@Cleanup Connection connection = DriverManager.getConnection(
//				"jdbc:postgres://localhost:5432/cinema",
//				"postgres",
//				"12345"
//				);
//		connection.createStatement()

		SpringApplication.run(CinemaApplication.class, args);
	}

}
