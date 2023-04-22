package com.blandygbc.med.voliapi.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroUsuario(
                @NotNull @Email String login,
                @NotNull String senha) {

}
