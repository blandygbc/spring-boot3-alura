package com.blandygbc.med.voliapi.paciente;

import com.blandygbc.med.voliapi.endereco.Endereco;

public record DadosDetalharPaciente(
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco) {

    public DadosDetalharPaciente(Paciente paciente) {
        this(
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getTelefone(),
                paciente.getCpf(),
                paciente.getEndereco());
    }

}
