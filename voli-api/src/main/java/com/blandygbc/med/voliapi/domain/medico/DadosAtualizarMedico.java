package com.blandygbc.med.voliapi.domain.medico;

import com.blandygbc.med.voliapi.domain.endereco.DadosEndereco;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizarMedico(
                @NotNull Long id,
                String nome,
                String telefone,
                @Valid DadosEndereco endereco) {
}
