package com.blandygbc.med.voliapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blandygbc.med.voliapi.domain.consulta.AgendaDeConsultasService;
import com.blandygbc.med.voliapi.domain.consulta.DadosAgendamentoConsulta;
import com.blandygbc.med.voliapi.domain.consulta.DadosCancelarAgendamento;
import com.blandygbc.med.voliapi.domain.consulta.DadosDetalharConsulta;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultasService agendaService;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalharConsulta> agendar(@RequestBody @Valid DadosAgendamentoConsulta agendamento) {
        agendaService.agendar(agendamento);
        return ResponseEntity.ok(new DadosDetalharConsulta(null, null, null, null));

    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<String> agendar(@RequestBody @Valid DadosCancelarAgendamento dadosCancelamento) {
        agendaService.cancelarAgendamento(dadosCancelamento);
        return ResponseEntity.ok("Consulta cancelada com sucesso!");

    }

}
