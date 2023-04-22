package com.blandygbc.med.voliapi.domain.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blandygbc.med.voliapi.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import com.blandygbc.med.voliapi.domain.consulta.validacoes.ValidadorCancelamentoDeConsulta;
import com.blandygbc.med.voliapi.domain.exception.ValidacaoException;
import com.blandygbc.med.voliapi.domain.medico.Medico;
import com.blandygbc.med.voliapi.domain.medico.MedicoRepository;
import com.blandygbc.med.voliapi.domain.paciente.PacienteRepository;

import jakarta.validation.Valid;

@Service
public class AgendaDeConsultasService {

    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadoresAgendamento;

    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    public DadosDetalharConsulta agendar(DadosAgendamentoConsulta dadosAgendamento) {

        validadoresAgendamento.forEach(validador -> validador.validar(dadosAgendamento));
        var paciente = pacienteRepository.getReferenceById(dadosAgendamento.idPaciente());
        var medico = escolherMedico(dadosAgendamento);
        if (medico == null) {
            throw new ValidacaoException("Não há medicos disponíveis na data e hora escolhida.");
        }

        var consulta = new Consulta(null, medico, paciente, dadosAgendamento.data(), null);
        repository.save(consulta);
        return new DadosDetalharConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dadosAgendamento) {
        if (dadosAgendamento.idMedico() != null) {
            return medicoRepository.getReferenceById(dadosAgendamento.idMedico());
        }
        if (dadosAgendamento.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido!");
        }
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dadosAgendamento.especialidade(),
                dadosAgendamento.data());
    }

    public void cancelarAgendamento(@Valid DadosCancelarAgendamento dadosCancelamento) {
        if (!repository.existsById(dadosCancelamento.idConsulta())) {
            throw new ValidacaoException("Consulta não encontrada.");
        }
        var consulta = repository.getReferenceById(dadosCancelamento.idConsulta());
        validadoresCancelamento.forEach(validador -> validador.validar(consulta, dadosCancelamento.motivo()));
        consulta.cancelar(dadosCancelamento.motivo());
    }
}
