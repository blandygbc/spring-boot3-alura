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

import com.blandygbc.med.voliapi.medico.DadosAtualizarMedico;
import com.blandygbc.med.voliapi.medico.DadosCadastroMedico;
import com.blandygbc.med.voliapi.medico.DadosDetalharMedico;
import com.blandygbc.med.voliapi.medico.DadosListagemMedico;
import com.blandygbc.med.voliapi.medico.Medico;
import com.blandygbc.med.voliapi.medico.MedicoRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicos")
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
        Optional<Medico> medicoOpt = medicoRepository.findById(id);
        if (medicoOpt.isPresent()) {
            return ResponseEntity.ok(new DadosDetalharMedico(medicoOpt.get()));
        } else {
            return ResponseEntity.notFound().build();
        }

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
        Optional<Medico> medicoOpt = medicoRepository.findById(id);
        if (medicoOpt.isPresent()) {
            medicoOpt.get().desativar();
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
