package com.blandygbc.med.voliapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blandygbc.med.voliapi.domain.usuario.DadosAutenticacao;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<Object> logar(@RequestBody @Valid DadosAutenticacao dadosAutenticacao) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dadosAutenticacao.login(),
                dadosAutenticacao.senha());
        Authentication authenticate = authenticationManager.authenticate(token);
        return ResponseEntity.ok().build();
    }
}
