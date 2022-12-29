package com.blandygbc.med.voliapi.controller;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blandygbc.med.voliapi.medico.DadosAtualizarMedico;
import com.blandygbc.med.voliapi.medico.DadosCadastroMedico;
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
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dadosCadastroMedico) {
        medicoRepository.save(new Medico(dadosCadastroMedico));
    }

    @GetMapping
    public Page<DadosListagemMedico> listar(
            @PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
        return medicoRepository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemMedico::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizarMedico dadosAtualizarMedico) {
        Medico medico = medicoRepository.getReferenceById(dadosAtualizarMedico.id());
        medico.atualizar(dadosAtualizarMedico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void desativar(@PathVariable Long id) {
        Optional<Medico> medicoOpt = medicoRepository.findById(id);
        if (medicoOpt.isPresent()) {
            medicoOpt.get().desativar();
        } else {
            throw new RuntimeException("Médico não encontrado");
        }
    }
}
