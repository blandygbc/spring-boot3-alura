package com.blandygbc.med.voliapi.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blandygbc.med.voliapi.medico.DadosCadastroMedico;

@RestController
@RequestMapping("/medicos")
public class MeidcoController {

    @PostMapping
    public void cadastrar(@RequestBody DadosCadastroMedico dadosCadastroMedico) {

    }
}
