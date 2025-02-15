package com.javaacademy.Cinema.controller;


import com.javaacademy.Cinema.dto.PlaceDto;
import com.javaacademy.Cinema.dto.SessionDto;
import com.javaacademy.Cinema.dto.SessionResponseDto;
import com.javaacademy.Cinema.entity.Session;
import com.javaacademy.Cinema.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SessionControler {
    private final SessionService sessionService;
    @Value("${admin.token}")
    private String admintoken;

    @PostMapping("/session")
    public ResponseEntity<Session> createSession(@RequestHeader("user-token") String token,
                                                 @RequestBody SessionDto sessionDto) {
        if ((!token.equals(admintoken)) || (token == null) || (token.isEmpty())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(sessionService.createSession(sessionDto), HttpStatus.CREATED);
    }

    @GetMapping("/session")
    public ResponseEntity<List<SessionResponseDto>> getAllSession() {
        return ResponseEntity.status(HttpStatus.OK).body(sessionService.getAllSession());
    }

    @GetMapping("/session/{id}/free-place")
    public ResponseEntity<List<PlaceDto>> getFreePlaceSession(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(sessionService.getFreePlaceSession(id));
    }


}
