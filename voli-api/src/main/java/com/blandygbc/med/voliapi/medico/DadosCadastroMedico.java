package com.blandygbc.med.voliapi.medico;

import com.blandygbc.med.voliapi.endereco.DadosEndereco;

public record DadosCadastroMedico(
        String nome,
        String crm,
        Especialidade especialidade,
        DadosEndereco endereco) {
}
