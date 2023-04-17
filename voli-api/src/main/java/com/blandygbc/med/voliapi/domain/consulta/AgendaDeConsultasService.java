package com.blandygbc.med.voliapi.domain.consulta;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blandygbc.med.voliapi.domain.exception.CancelamentoInvalidoException;
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

    public void agendar(DadosAgendamentoConsulta dadosAgendamento) {
        var paciente = pacienteRepository.getReferenceById(dadosAgendamento.idPaciente());
        Medico medico = escolherMedico(dadosAgendamento);
        var consulta = new Consulta(null, medico, paciente, dadosAgendamento.data(), null);
        repository.save(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dadosAgendamento) {
        if (dadosAgendamento.idMedico() != null)
            return medicoRepository.getReferenceById(dadosAgendamento.idMedico());
        if (dadosAgendamento.especialidade() != null)
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido!");
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dadosAgendamento.especialidade(),
                dadosAgendamento.data());
    }

    public void cancelarAgendamento(@Valid DadosCancelarAgendamento dadosCancelamento) {
        var consulta = repository.getReferenceById(dadosCancelamento.idConsulta());
        if (LocalDateTime.now().isBefore(consulta.getData().minusHours(24))) {
            throw new CancelamentoInvalidoException("A consulta só pode ser cancelada com 24h de antecedência");
        }
        consulta.cancelar(dadosCancelamento.motivo());
    }
}
