package app.projeto.service;

import app.projeto.entity.Usuario;
import app.projeto.entity.Vaga;
import app.projeto.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("TESTES DE UNIDADE – UsuarioService")
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve lançar exceção ao criar usuário sem vagas")
    void deveLancarExcecaoQuandoVagasVazia() {
        Usuario usuario = new Usuario();
        usuario.setNome("João Teste");
        usuario.setVagas(new ArrayList<>());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.criar(usuario);
        });

        assertEquals("Não é possível cadastrar usuário sem associar vaga", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve criar usuário com vagas válidas")
    void deveCriarUsuarioComVagasValidas() {
        Usuario usuario = new Usuario();
        usuario.setNome("João Teste");
        List<Vaga> vagas = new ArrayList<>();
        vagas.add(new Vaga());
        usuario.setVagas(vagas);

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario salvo = usuarioService.criar(usuario);

        assertNotNull(salvo);
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve listar todos os usuários")
    void deveListarTodosUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(new Usuario()));
        assertFalse(usuarioService.listar().isEmpty());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve buscar usuário por ID")
    void deveBuscarUsuarioPorId() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(usuario));
        assertNotNull(usuarioService.buscarPorId(1L));
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve deletar usuário")
    void deveDeletarUsuario() {
        usuarioService.deletar(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }
}
