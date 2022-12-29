package com.blandygbc.med.voliapi.paciente;

import com.blandygbc.med.voliapi.endereco.DadosEndereco;

import jakarta.validation.Valid;

public record DadosAtualizarPaciente(
                Long id,
                String nome,
                String telefone,
                @Valid DadosEndereco endereco) {
}
