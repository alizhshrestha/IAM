package com.himalayas.authserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OAuth2ExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handle(Exception ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("OAuth2 error: " + ex.getMessage());
  }
}
