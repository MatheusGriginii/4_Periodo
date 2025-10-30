package app.projeto.controller;

import app.projeto.entity.Empresa;
import app.projeto.service.EmpresaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmpresaController.class)
@DisplayName("TESTES DE INTEGRAÇÃO – EmpresaController")
public class EmpresaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpresaService empresaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Empresa empresa;

    @BeforeEach
    void setup() {
        empresa = new Empresa();
        empresa.setNome("Empresa Teste");
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Deve retornar 200 ao cadastrar empresa válida")
    void deveCadastrarEmpresaValida() throws Exception {
        when(empresaService.criar(any(Empresa.class))).thenReturn(empresa);

        mockMvc.perform(post("/empresas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empresa)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Deve retornar erro ao cadastrar empresa sem nome")
    void deveRetornarErroNomeVazio() throws Exception {
        empresa.setNome("");

        when(empresaService.criar(any(Empresa.class)))
                .thenThrow(new RuntimeException("Nome da empresa é obrigatório"));

        mockMvc.perform(post("/empresas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empresa)))
                .andExpect(status().isBadRequest());
    }
}
