package com.blandygbc.med.voliapi.domain.medico;

import com.blandygbc.med.voliapi.domain.endereco.Endereco;

public record DadosDetalharMedico(
        Long id,
        String nome,
        String email,
        String telefone,
        String crm,
        Especialidade especialidade,
        Endereco endereco) {

    public DadosDetalharMedico(Medico medico) {
        this(
                medico.getId(),
                medico.getNome(),
                medico.getEmail(),
                medico.getTelefone(),
                medico.getCrm(),
                medico.getEspecialidade(),
                medico.getEndereco());
    }

}
