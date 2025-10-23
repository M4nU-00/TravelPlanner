package it.planner.travel.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import it.planner.travel.domain.dto.response.ErrorResponseDto;
import it.planner.travel.exception.ObjectNotFoundException;
import it.planner.travel.exception.base.BaseException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Gestione specifica di ObjectNotFoundException
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleObjectNotFoundException(ObjectNotFoundException ex) {
        log.error("ObjectNotFoundException occurred: {} - Status: {}", ex.getMessage(), ex.getHttpStatus(), ex);

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ex.getErrorResponse());
    }

    // Gestione generale di BaseException
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponseDto> handleBaseException(BaseException ex) {
        log.error("BaseException occurred: {} - Status: {}", ex.getMessage(), ex.getHttpStatus(), ex);

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ex.getErrorResponse());
    }

}
