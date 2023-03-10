package com.blandygbc.med.voliapi.domain.paciente;

import com.blandygbc.med.voliapi.domain.endereco.Endereco;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Paciente")
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String cpf;
    private String telefone;

    @Embedded
    private Endereco endereco;

    private boolean ativo;

    public Paciente(DadosCadastroPaciente dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizar(DadosAtualizarPaciente dadosAtualizarPaciente) {
        if (dadosAtualizarPaciente.nome() != null)
            this.nome = dadosAtualizarPaciente.nome();

        if (dadosAtualizarPaciente.telefone() != null)
            this.telefone = dadosAtualizarPaciente.telefone();

        if (dadosAtualizarPaciente.endereco() != null)
            this.endereco.atualizar(dadosAtualizarPaciente.endereco());
    }

    public void desativar() {
        this.ativo = false;
    }

}
