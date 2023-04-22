package com.blandygbc.med.voliapi.domain.consulta.validacoes;

import com.blandygbc.med.voliapi.domain.consulta.Consulta;
import com.blandygbc.med.voliapi.domain.consulta.MotivoCancelamento;

public interface ValidadorCancelamentoDeConsulta {
    public void validar(Consulta consulta, MotivoCancelamento motivo);
}
