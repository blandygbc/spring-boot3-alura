package com.blandygbc.med.voliapi.domain.paciente;

import com.blandygbc.med.voliapi.domain.endereco.DadosEndereco;

import jakarta.validation.Valid;

public record DadosAtualizarPaciente(
                Long id,
                String nome,
                String telefone,
                @Valid DadosEndereco endereco) {
}
