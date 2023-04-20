package com.blandygbc.med.voliapi.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.blandygbc.med.voliapi.domain.paciente.DadosAtualizarPaciente;
import com.blandygbc.med.voliapi.domain.paciente.DadosCadastroPaciente;
import com.blandygbc.med.voliapi.domain.paciente.DadosDetalharPaciente;
import com.blandygbc.med.voliapi.domain.paciente.DadosListagemPaciente;
import com.blandygbc.med.voliapi.domain.paciente.Paciente;
import com.blandygbc.med.voliapi.domain.paciente.PacienteRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalharPaciente> cadastrar(
            @RequestBody @Valid DadosCadastroPaciente dadosCadastroPaciente,
            UriComponentsBuilder uriComponentsBuilder) {
        Paciente paciente = pacienteRepository.save(new Paciente(dadosCadastroPaciente));
        URI uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalharPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(
            @PageableDefault(page = 0, size = 10, sort = { "nome" }) Pageable paginacao) {
        Page<DadosListagemPaciente> page = pacienteRepository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemPaciente::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalharPaciente> detalhar(@PathVariable Long id) {
        Optional<Paciente> pacienteOpt = pacienteRepository.findById(id);
        if (pacienteOpt.isPresent()) {
            return ResponseEntity.ok(new DadosDetalharPaciente(pacienteOpt.get()));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalharPaciente> atualizar(
            @RequestBody @Valid DadosAtualizarPaciente dadosAtualizarPaciente) {
        Paciente paciente = pacienteRepository.getReferenceById(dadosAtualizarPaciente.id());
        paciente.atualizar(dadosAtualizarPaciente);
        return ResponseEntity.ok(new DadosDetalharPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> remover(@PathVariable Long id) {
        Optional<Paciente> pacienteOpt = pacienteRepository.findById(id);
        if (pacienteOpt.isPresent()) {
            pacienteOpt.get().desativar();
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
