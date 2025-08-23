package com.gustavosdaniel.authservice.exception;

import com.gustavosdaniel.authservice.commun.FusoHorarioBr;
import com.gustavosdaniel.authservice.user.EmailAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.warn("Validation failed {}", exception.getMessage());

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Validation failed");
        errorDTO.setMessage("One or more fields are invalid");
        errorDTO.setTimestamp(FusoHorarioBr.nowInBrasil().toLocalDateTime());

        Map<String,String> fieldErros = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            fieldErros.put(error.getField(), error.getDefaultMessage());
        });

        errorDTO.setFieldErrors(fieldErros);

        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception exception) {

        log.error("Caught exception", exception);

        ErrorDTO errorDTO = ErrorDTO.builder()
                .error("Internal Server Error")
                .message("An unexpected error occurred. Please try again later.")
                .timestamp(FusoHorarioBr.nowInBrasil().toLocalDateTime())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);


    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception) {

        log.error("Caught EmailAlreadyExistsException", exception);

        ErrorDTO errorDTO = ErrorDTO.builder()
                .error("Bad Request")
                .message("Email já em uso.")
                .timestamp(FusoHorarioBr.nowInBrasil().toLocalDateTime())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);


    }

}
