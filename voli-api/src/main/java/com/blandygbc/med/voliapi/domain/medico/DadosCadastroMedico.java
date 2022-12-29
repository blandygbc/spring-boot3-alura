package com.blandygbc.med.voliapi.domain.medico;

import com.blandygbc.med.voliapi.domain.endereco.DadosEndereco;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroMedico(
                // A mensagem pode ser setada por aqui diretamente (message = "Nome é
                // obrigatório")
                // Ou pode ser usado o arquivo ValidationMessages.properties
                @NotBlank(message = "{nome.obrigatorio}") String nome,
                @NotBlank(message = "{email.obrigatorio}") @Email(message = "{email.invalido}") String email,
                @NotBlank String telefone,
                @NotBlank @Pattern(regexp = "\\d{4,6}") String crm,
                @NotNull Especialidade especialidade,
                @NotNull @Valid DadosEndereco endereco) {
}
