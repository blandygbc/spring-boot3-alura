package com.blandygbc.med.voliapi.domain.consulta;

import java.time.LocalDateTime;

public record DadosDetalharConsulta(
                Long id,
                Long idMedico,
                Long idPaciente,
                LocalDateTime data) {

        public DadosDetalharConsulta(Consulta consulta) {
                this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(),
                                consulta.getData());
        }

}
