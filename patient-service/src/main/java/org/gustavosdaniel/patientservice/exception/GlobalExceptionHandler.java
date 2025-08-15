package org.gustavosdaniel.patientservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.gustavosdaniel.patientservice.commun.FusoHorarioBr;
import org.gustavosdaniel.patientservice.patient.EmailAlreadyExistsException;
import org.gustavosdaniel.patientservice.patient.PatientNotFoundException;
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

        log.warn("Email already exists: {}", exception.getMessage());

        ErrorDTO errorDTO = ErrorDTO.builder()
                .error("Email já existe")
                .message("O email informado já está sendo utilizado por outro usuário")
                .timestamp(FusoHorarioBr.nowInBrasil().toLocalDateTime())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDTO);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorDTO> handlePatientNotFoundException(PatientNotFoundException exception) {

        log.warn("Patient not found: {}", exception.getMessage());

        ErrorDTO errorDTO = ErrorDTO.builder()
                .error("Paciente não encontrado")
                .message("Não foi possível encontrar um paciente com o ID informado")
                .timestamp(FusoHorarioBr.nowInBrasil().toLocalDateTime())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }
}
