package com.blandygbc.med.voliapi.domain.consulta.validacoes;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.blandygbc.med.voliapi.domain.consulta.DadosAgendamentoConsulta;
import com.blandygbc.med.voliapi.domain.exception.ValidacaoException;

@Component
public class ValidadorHorarioAntecedenciaDeAgendamento implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dadosAgendamento) {
        LocalDateTime dataConsulta = dadosAgendamento.data();
        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();
        if (diferencaEmMinutos < 30) {
            throw new ValidacaoException("A consulta dever ser agendada com uma antecedencia mínima de 30 minutos.");

        }
    }
}
