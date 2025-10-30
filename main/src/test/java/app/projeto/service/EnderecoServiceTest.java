package app.projeto.service;

import app.projeto.entity.Endereco;
import app.projeto.repository.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("TESTES DE UNIDADE – EnderecoService")
public class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve salvar endereço válido")
    void deveSalvarEnderecoValido() {
        Endereco endereco = new Endereco();
        endereco.setCidade("São Paulo");

        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        Endereco salvo = enderecoService.salvar(endereco);

        assertNotNull(salvo);
        verify(enderecoRepository, times(1)).save(endereco);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve listar todos os endereços")
    void deveListarTodosEnderecos() {
        when(enderecoRepository.findAll()).thenReturn(List.of(new Endereco()));

        List<Endereco> enderecos = enderecoService.listarTodos();

        assertNotNull(enderecos);
        assertFalse(enderecos.isEmpty());
        verify(enderecoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve buscar endereço por ID")
    void deveBuscarEnderecoPorId() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        when(enderecoRepository.findById(1L)).thenReturn(java.util.Optional.of(endereco));
        assertTrue(enderecoService.buscarPorId(1L).isPresent());
        verify(enderecoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve deletar endereço")
    void deveDeletarEndereco() {
        enderecoService.deletar(1L);
        verify(enderecoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve buscar endereços por cidade")
    void deveBuscarEnderecoPorCidade() {
        when(enderecoRepository.findByCidadeIgnoreCaseContaining("São Paulo")).thenReturn(List.of(new Endereco()));
        assertFalse(enderecoService.buscarPorCidade("São Paulo").isEmpty());
        verify(enderecoRepository, times(1)).findByCidadeIgnoreCaseContaining("São Paulo");
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve buscar endereços por estado")
    void deveBuscarEnderecoPorEstado() {
        when(enderecoRepository.findByEstadoIgnoreCaseContaining("SP")).thenReturn(List.of(new Endereco()));
        assertFalse(enderecoService.buscarPorEstado("SP").isEmpty());
        verify(enderecoRepository, times(1)).findByEstadoIgnoreCaseContaining("SP");
    }
}
