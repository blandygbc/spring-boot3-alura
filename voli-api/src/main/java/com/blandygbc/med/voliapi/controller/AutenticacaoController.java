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

import com.blandygbc.med.voliapi.domain.usuario.AutenticacaoService;
import com.blandygbc.med.voliapi.domain.usuario.DadosAutenticacao;
import com.blandygbc.med.voliapi.domain.usuario.DadosCadastroUsuario;
import com.blandygbc.med.voliapi.domain.usuario.DadosUsuario;
import com.blandygbc.med.voliapi.domain.usuario.Usuario;
import com.blandygbc.med.voliapi.infra.security.DadosTokenJWT;
import com.blandygbc.med.voliapi.infra.security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @PostMapping("/login")
    public ResponseEntity<DadosTokenJWT> logar(@RequestBody @Valid DadosAutenticacao dadosAutenticacao) {
        var authTtoken = new UsernamePasswordAuthenticationToken(dadosAutenticacao.login(),
                dadosAutenticacao.senha());
        Authentication authenticate = authenticationManager.authenticate(authTtoken);
        String tokenJWT = tokenService.gerarToken((Usuario) authenticate.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    @PostMapping
    public ResponseEntity<DadosUsuario> cadastrar(@RequestBody @Valid DadosCadastroUsuario dadosCadastro) {
        var savedUser = autenticacaoService.criarUsuario(dadosCadastro);
        return ResponseEntity.ok(new DadosUsuario(savedUser));
    }
}
