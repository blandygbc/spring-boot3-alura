package com.blandygbc.med.voliapi.domain.medico;

import com.blandygbc.med.voliapi.domain.endereco.Endereco;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    @Embedded
    private Endereco endereco;
    private boolean ativo;

    public Medico(DadosCadastroMedico dadosCadastroMedico) {
        this.ativo = true;
        this.nome = dadosCadastroMedico.nome();
        this.email = dadosCadastroMedico.email();
        this.telefone = dadosCadastroMedico.telefone();
        this.crm = dadosCadastroMedico.crm();
        this.especialidade = dadosCadastroMedico.especialidade();
        this.endereco = new Endereco(dadosCadastroMedico.endereco());
    }

    public void atualizar(DadosAtualizarMedico dadosAtualizarMedico) {
        if (dadosAtualizarMedico.nome() != null)
            this.nome = dadosAtualizarMedico.nome();
        if (dadosAtualizarMedico.telefone() != null)
            this.telefone = dadosAtualizarMedico.telefone();
        if (dadosAtualizarMedico.endereco() != null)
            this.endereco.atualizar(dadosAtualizarMedico.endereco());
    }

    public void desativar() {
        this.ativo = false;
    }
}
