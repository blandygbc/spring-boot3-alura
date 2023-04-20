package com.blandygbc.med.voliapi.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blandygbc.med.voliapi.domain.consulta.ConsultaRepository;
import com.blandygbc.med.voliapi.domain.consulta.DadosAgendamentoConsulta;
import com.blandygbc.med.voliapi.domain.exception.ValidacaoException;

@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dadosAgendamento) {
        var primeiroHorario = dadosAgendamento.data().withHour(7);
        var ultimoHorario = dadosAgendamento.data().withHour(18);
        Boolean pacientePossuiOutraConsultaNoDia = repository
                .existsByPacienteIdAndDataBetween(dadosAgendamento.idPaciente(), primeiroHorario, ultimoHorario);
        if (Boolean.TRUE.equals(pacientePossuiOutraConsultaNoDia)) {
            throw new ValidacaoException("Paciente já possui uma consulta agendada nesse dia.");
        }
    }
}
