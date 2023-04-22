package com.blandygbc.med.voliapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.blandygbc.med.voliapi.TestsEnvProperties;
import com.blandygbc.med.voliapi.domain.consulta.AgendaDeConsultasService;
import com.blandygbc.med.voliapi.domain.consulta.DadosAgendamentoConsulta;
import com.blandygbc.med.voliapi.domain.consulta.DadosDetalharConsulta;
import com.blandygbc.med.voliapi.domain.medico.Especialidade;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoJson;

    @Autowired
    private JacksonTester<DadosDetalharConsulta> dadosDetalharJson;

    @MockBean
    private AgendaDeConsultasService agendaDeConsultasService;

    @BeforeAll
    public static void setup() {
        TestsEnvProperties.load();
    }

    @Test
    @DisplayName("Deveria devolver código 400 quando as informações estão inválidas")
    @WithMockUser
    void testAgendarCenario1() throws Exception {

        var response = mvc.perform(post("/consultas")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código 200 quando as informações estão válidas")
    @WithMockUser
    void testAgendarCenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.GINECOLOGIA;
        var dadosConsulta = new DadosDetalharConsulta(null, 2l, 5l, data);
        var dadosAgendamento = new DadosAgendamentoConsulta(
                2l,
                5l,
                data,
                especialidade);

        when(agendaDeConsultasService.agendar(any()))
                .thenReturn(dadosConsulta);

        var response = mvc.perform(
                post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAgendamentoJson
                                .write(dadosAgendamento)
                                .getJson()))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        var jsonEsperado = dadosDetalharJson.write(dadosConsulta).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}
