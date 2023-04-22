package com.blandygbc.med.voliapi.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record DadosCancelarAgendamento(
        @NotNull Long idConsulta,
        @NotNull MotivoCancelamento motivo) {

}
