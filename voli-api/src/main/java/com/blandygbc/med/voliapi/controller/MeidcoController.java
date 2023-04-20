package com.blandygbc.med.voliapi.controller;

import java.net.URI;

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

import com.blandygbc.med.voliapi.domain.medico.DadosAtualizarMedico;
import com.blandygbc.med.voliapi.domain.medico.DadosCadastroMedico;
import com.blandygbc.med.voliapi.domain.medico.DadosDetalharMedico;
import com.blandygbc.med.voliapi.domain.medico.DadosListagemMedico;
import com.blandygbc.med.voliapi.domain.medico.Medico;
import com.blandygbc.med.voliapi.domain.medico.MedicoRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MeidcoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalharMedico> cadastrar(@RequestBody @Valid DadosCadastroMedico dadosCadastroMedico,
            UriComponentsBuilder uriComponentsBuilder) {
        Medico medico = medicoRepository.save(new Medico(dadosCadastroMedico));
        URI uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalharMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(
            @PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
        Page<DadosListagemMedico> page = medicoRepository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalharMedico> detalhar(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalharMedico(medico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalharMedico> atualizar(
            @RequestBody @Valid DadosAtualizarMedico dadosAtualizarMedico) {
        Medico medico = medicoRepository.getReferenceById(dadosAtualizarMedico.id());
        medico.atualizar(dadosAtualizarMedico);
        return ResponseEntity.ok(new DadosDetalharMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> desativar(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desativar();
        return ResponseEntity.noContent().build();
    }
}
