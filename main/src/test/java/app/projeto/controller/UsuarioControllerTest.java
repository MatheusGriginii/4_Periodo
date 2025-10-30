package app.projeto.controller;

import app.projeto.entity.Endereco;
import app.projeto.entity.Usuario;
import app.projeto.entity.Vaga;
import app.projeto.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@DisplayName("TESTES DE INTEGRAÇÃO – UsuarioController")
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuario;

    @BeforeEach
    void setup() {
        usuario = new Usuario();
        usuario.setNome("João Teste");
        Endereco endereco = new Endereco();
        endereco.setCidade("São Paulo");
        usuario.setEndereco(endereco);
        List<Vaga> vagas = new ArrayList<>();
        vagas.add(new Vaga());
        usuario.setVagas(vagas);
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Deve retornar 200 ao cadastrar usuário válido")
    void deveCadastrarUsuarioValido() throws Exception {
        when(usuarioService.criar(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Deve retornar erro ao cadastrar usuário sem vagas")
    void deveRetornarErroSemVagas() throws Exception {
        usuario.setVagas(new ArrayList<>());

        when(usuarioService.criar(any(Usuario.class)))
                .thenThrow(new RuntimeException("Não é possível cadastrar usuário sem associar vaga"));

        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Deve listar todos os usuários")
    void deveListarUsuarios() throws Exception {
        when(usuarioService.listar()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk());
    }
}
