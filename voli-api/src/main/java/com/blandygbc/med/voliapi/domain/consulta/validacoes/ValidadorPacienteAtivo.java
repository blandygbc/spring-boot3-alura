package com.blandygbc.med.voliapi.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blandygbc.med.voliapi.domain.consulta.DadosAgendamentoConsulta;
import com.blandygbc.med.voliapi.domain.exception.ValidacaoException;
import com.blandygbc.med.voliapi.domain.paciente.PacienteRepository;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private PacienteRepository repository;

    public void validar(DadosAgendamentoConsulta dadosAgendamento) {
        if (!repository.existsById(dadosAgendamento.idPaciente())) {
            throw new ValidacaoException("O paciente informado não foi encontrado.");
        }
        var pacienteEstaAtivo = repository.findAtivoById(dadosAgendamento.idPaciente());
        if (Boolean.FALSE.equals(pacienteEstaAtivo)) {
            throw new ValidacaoException("Consulta não pode ser agendada com paciente inativo.");
        }
    }
}
