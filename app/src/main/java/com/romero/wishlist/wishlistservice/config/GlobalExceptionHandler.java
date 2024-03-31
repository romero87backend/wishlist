package com.romero.wishlist.wishlistservice.config;

import com.romero.wishlist.wishlistservice.application.exceptions.FavoritoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FavoritoNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(FavoritoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
