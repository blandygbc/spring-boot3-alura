package com.blandygbc.med.voliapi.domain.consulta.validacoes;

import java.time.LocalDateTime;

import com.blandygbc.med.voliapi.domain.consulta.Consulta;
import com.blandygbc.med.voliapi.domain.consulta.MotivoCancelamento;
import com.blandygbc.med.voliapi.domain.exception.CancelamentoInvalidoException;

public class ValidadorCancelarComAntecedencia implements ValidadorCancelamentoDeConsulta {

    @Override
    public void validar(Consulta consulta, MotivoCancelamento motivo) {

        if (!LocalDateTime.now().isBefore(consulta.getData().minusHours(24))) {
            throw new CancelamentoInvalidoException("A consulta só pode ser cancelada com 24h de antecedência");
        }
    }

}
