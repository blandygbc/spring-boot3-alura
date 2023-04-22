package com.blandygbc.med.voliapi.domain.consulta.validacoes;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.blandygbc.med.voliapi.domain.consulta.Consulta;
import com.blandygbc.med.voliapi.domain.consulta.MotivoCancelamento;
import com.blandygbc.med.voliapi.domain.exception.CancelamentoInvalidoException;

@Component
public class ValidadorCancelarComAntecedencia implements ValidadorCancelamentoDeConsulta {

    @Override
    public void validar(Consulta consulta, MotivoCancelamento motivo) {

        if (!LocalDateTime.now().isBefore(consulta.getData().minusHours(24))) {
            throw new CancelamentoInvalidoException("A consulta só pode ser cancelada com 24h de antecedência");
        }
    }

}
