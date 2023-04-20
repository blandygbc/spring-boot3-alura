package com.blandygbc.med.voliapi.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blandygbc.med.voliapi.domain.consulta.DadosAgendamentoConsulta;
import com.blandygbc.med.voliapi.domain.exception.ValidacaoException;
import com.blandygbc.med.voliapi.domain.medico.MedicoRepository;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private MedicoRepository repository;

    public void validar(DadosAgendamentoConsulta dadosAgendamento) {
        if (dadosAgendamento.idMedico() == null) {
            return;
        }
        if (!repository.existsById(dadosAgendamento.idMedico())) {
            throw new ValidacaoException("O médico informado não foi encontrado.");
        }
        var medicoEstaAtivo = repository.findAtivoById(dadosAgendamento.idMedico());
        if (Boolean.FALSE.equals(medicoEstaAtivo)) {
            throw new ValidacaoException("Consulta não pode ser agendada com médico inativo.");
        }
    }
}
