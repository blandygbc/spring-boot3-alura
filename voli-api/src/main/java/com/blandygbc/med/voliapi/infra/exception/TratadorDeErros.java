package com.blandygbc.med.voliapi.infra.exception;

import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blandygbc.med.voliapi.domain.exception.CancelamentoInvalidoException;
import com.blandygbc.med.voliapi.domain.exception.ValidacaoException;
import com.blandygbc.med.voliapi.util.JsonMessage;

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

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<JsonMessage> tratarErrValidacao(ValidacaoException ex) {

        return ResponseEntity.badRequest().body(new JsonMessage(ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<JsonMessage> tratarErro400(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(new JsonMessage(ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<JsonMessage> tratarErroBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JsonMessage("Credenciais inválidas"));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<JsonMessage> tratarErroAuthentication() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JsonMessage("Falha na autenticação"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<JsonMessage> tratarErroAcessoNegado() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonMessage("Acesso negado"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonMessage> tratarErro500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JsonMessage(ex.getLocalizedMessage()));
    }

    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
