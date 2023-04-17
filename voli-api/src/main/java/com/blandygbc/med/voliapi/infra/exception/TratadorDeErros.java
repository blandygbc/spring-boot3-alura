package com.blandygbc.med.voliapi.infra.exception;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blandygbc.med.voliapi.domain.exception.CancelamentoInvalidoException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(CancelamentoInvalidoException.class)
    public ResponseEntity<JsonMessage> tratarCancelamentoInvalido(CancelamentoInvalidoException ex) {
        return ResponseEntity.badRequest().body(new JsonMessage(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> tratarErro400(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(fieldErrors.stream().map(DadosErroValidacao::new).toList());
    }

    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    private record JsonMessage(String message) {
    }
}
