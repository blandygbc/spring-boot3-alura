package com.blandygbc.med.voliapi.medico;

import com.blandygbc.med.voliapi.endereco.DadosEndereco;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizarMedico(
                @NotNull Long id,
                String nome,
                String telefone,
                @Valid DadosEndereco endereco) {
}
