package com.blandygbc.med.voliapi.domain.consulta;

import java.time.LocalDateTime;

import com.blandygbc.med.voliapi.domain.medico.Especialidade;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record DadosAgendamentoConsulta(
                Long idMedico,
                @NotNull Long idPaciente,
                @NotNull @Future LocalDateTime data,
                Especialidade especialidade) {

}
