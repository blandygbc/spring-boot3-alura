package com.blandygbc.med.voliapi.domain.usuario;

public record DadosUsuario(
        Long id,
        String login) {
    public DadosUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getLogin());
    }
}
