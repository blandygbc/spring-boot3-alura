package com.blandygbc.med.voliapi.domain.consulta.validacoes;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import com.blandygbc.med.voliapi.domain.consulta.DadosAgendamentoConsulta;
import com.blandygbc.med.voliapi.domain.exception.ValidacaoException;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dadosAgendamento) {
        var dataConsulta = dadosAgendamento.data();
        var ehDomingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var ehAntesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        var ehDepisDoFechamentoDaClinica = dataConsulta.getHour() > 18;
        if (ehDomingo || ehAntesDaAberturaDaClinica || ehDepisDoFechamentoDaClinica) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
        }
    }
}
