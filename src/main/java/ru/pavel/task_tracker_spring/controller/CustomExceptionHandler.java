package ru.pavel.task_tracker_spring.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.pavel.task_tracker_spring.exception.DeadlineException;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidDateTimeFormat(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException && cause.getMessage().contains("LocalDateTime")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Неверный формат времени. Ожидается формат: yyyy-MM-dd'T'HH:mm:ss");
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Ошибка в теле запроса: " + ex.getMessage());
    }

    @ExceptionHandler(DeadlineException.class)
    public ResponseEntity<String> handleDeadlineInPast(DeadlineException ex) {
        return ResponseEntity.status(422).body(ex.getMessage());
    }
}
