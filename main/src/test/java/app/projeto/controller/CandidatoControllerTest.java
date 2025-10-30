package app.projeto.controller;

import app.projeto.entity.Candidato;
import app.projeto.service.CandidatoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CandidatoController.class)
@DisplayName("TESTES DE INTEGRAÇÃO – CandidatoController")
public class CandidatoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandidatoService candidatoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Candidato candidato;

    @BeforeEach
    void setup() {
        candidato = new Candidato();
        candidato.setNome("João Teste");
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Deve retornar 201 ao cadastrar candidato válido")
    void deveCadastrarCandidatoValido() throws Exception {
        Mockito.when(candidatoService.criar(any(Candidato.class))).thenReturn(candidato);

        mockMvc.perform(post("/candidatos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(candidato)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Deve retornar 400 ao tentar cadastrar candidato sem nome")
    void deveRetornar400NomeVazio() throws Exception {
        candidato.setNome("");

        Mockito.when(candidatoService.criar(any(Candidato.class)))
                .thenThrow(new RuntimeException("O campo nome do candidato é obrigatório"));

        mockMvc.perform(post("/candidatos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(candidato)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Deve listar todos os candidatos")
    void deveListarCandidatos() throws Exception {
        Mockito.when(candidatoService.listar()).thenReturn(List.of(candidato));

        mockMvc.perform(get("/candidatos"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Deve buscar candidato por ID")
    void deveBuscarCandidatoPorId() throws Exception {
        candidato.setId(1L);
        Mockito.when(candidatoService.buscarPorId(1L)).thenReturn(candidato);

        mockMvc.perform(get("/candidatos/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Deve atualizar candidato")
    void deveAtualizarCandidato() throws Exception {
        candidato.setId(1L);
        Mockito.when(candidatoService.atualizar(any(Long.class), any(Candidato.class))).thenReturn(candidato);

        mockMvc.perform(put("/candidatos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(candidato)))
                .andExpect(status().isOk());
    }
}
