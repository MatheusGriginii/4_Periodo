package app.projeto.controller;

import app.projeto.entity.Endereco;
import app.projeto.service.EnderecoService;
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

@WebMvcTest(EnderecoController.class)
@DisplayName("TESTES DE INTEGRAÇÃO – EnderecoController")
public class EnderecoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnderecoService enderecoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Endereco endereco;

    @BeforeEach
    void setup() {
        endereco = new Endereco();
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Deve retornar 201 ao cadastrar endereço válido")
    void deveCadastrarEnderecoValido() throws Exception {
        when(enderecoService.salvar(any(Endereco.class))).thenReturn(endereco);

        mockMvc.perform(post("/enderecos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(endereco)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Deve processar requisição de endereço")
    void deveProcessarRequisicaoEndereco() throws Exception {
        when(enderecoService.salvar(any(Endereco.class))).thenReturn(endereco);

        mockMvc.perform(post("/enderecos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(endereco)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Deve listar todos os endereços")
    void deveListarEnderecos() throws Exception {
        when(enderecoService.listarTodos()).thenReturn(List.of(endereco));

        mockMvc.perform(get("/enderecos"))
                .andExpect(status().isOk());
    }
}
