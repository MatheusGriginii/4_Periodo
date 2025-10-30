package app.projeto.controller;

import app.projeto.entity.Empresa;
import app.projeto.entity.Vaga;
import app.projeto.service.VagaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VagaController.class)
@DisplayName("TESTES DE INTEGRAÇÃO – VagaController")
public class VagaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VagaService vagaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Vaga vaga;

    @BeforeEach
    void setup() {
        vaga = new Vaga();
        vaga.setTitulo("Desenvolvedor Java");
        Empresa empresa = new Empresa();
        empresa.setNome("Empresa Teste");
        vaga.setEmpresa(empresa);
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Deve retornar 200 ao cadastrar vaga válida")
    void deveCadastrarVagaValida() throws Exception {
        when(vagaService.criar(any(Vaga.class))).thenReturn(vaga);

        mockMvc.perform(post("/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vaga)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Deve retornar erro ao cadastrar vaga sem empresa")
    void deveRetornarErroSemEmpresa() throws Exception {
        vaga.setEmpresa(null);

        when(vagaService.criar(any(Vaga.class)))
                .thenThrow(new RuntimeException("Não é possível cadastrar vaga sem empresa associada."));

        mockMvc.perform(post("/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vaga)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Deve listar todas as vagas")
    void deveListarVagas() throws Exception {
        when(vagaService.listar()).thenReturn(List.of(vaga));

        mockMvc.perform(get("/vagas"))
                .andExpect(status().isOk());
    }
}
