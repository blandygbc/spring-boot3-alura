package com.blandygbc.med.voliapi.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blandygbc.med.voliapi.domain.consulta.ConsultaRepository;
import com.blandygbc.med.voliapi.domain.consulta.DadosAgendamentoConsulta;
import com.blandygbc.med.voliapi.domain.exception.ValidacaoException;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dadosAgendamento) {
        Boolean medicoPossuiOutraConsultaNoMesmoHorario = repository
                .existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dadosAgendamento.idMedico(),
                        dadosAgendamento.data());
        if (Boolean.TRUE.equals(medicoPossuiOutraConsultaNoMesmoHorario)) {
            throw new ValidacaoException("Médico já possui outra consulta agendada nesse mesmo horário.");
        }
    }
}
