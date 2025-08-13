package org.gustavosdaniel.patientservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("Validation failed {}", exception.getMessage());

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Validation failed");
        errorDTO.setMessage("One or more fields are invalid");
        errorDTO.setTimestamp(LocalDateTime.now());

        Map<String,String> fieldErros = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            fieldErros.put(error.getField(), error.getDefaultMessage());
        });

        errorDTO.setFildErrors(fieldErros);

        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception exception) {

        log.error("Caught exception", exception);

        ErrorDTO errorDTO = ErrorDTO.builder()
                .error("Internal Server Error")
                .message("An unexpected error occurred. Please try again later.")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }
}
