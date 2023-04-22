package com.blandygbc.med.voliapi.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(username);
    }

    public Usuario criarUsuario(DadosCadastroUsuario dadosCadastro) {
        var encryptedPass = passordEncoder.encode(dadosCadastro.senha());
        return usuarioRepository.save(new Usuario(null, dadosCadastro.login(), encryptedPass));
    }

}
