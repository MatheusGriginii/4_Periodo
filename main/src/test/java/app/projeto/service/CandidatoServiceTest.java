package app.projeto.service;

import app.projeto.entity.Candidato;
import app.projeto.repository.CandidatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("TESTES DE UNIDADE – CandidatoService")
public class CandidatoServiceTest {

    @Mock
    private CandidatoRepository candidatoRepository;

    @InjectMocks
    private CandidatoService candidatoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve lançar exceção ao tentar criar candidato sem nome")
    void deveLancarExcecaoQuandoNomeVazio() {
        // Arrange
        Candidato candidato = new Candidato();
        candidato.setNome("");

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            candidatoService.criar(candidato);
        });

        assertEquals("O campo nome do candidato é obrigatório", exception.getMessage());
        verify(candidatoRepository, never()).save(any(Candidato.class));
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve criar candidato com nome válido")
    void deveCriarCandidatoComNomeValido() {
        // Arrange
        Candidato candidato = new Candidato();
        candidato.setNome("João Teste");

        when(candidatoRepository.save(any(Candidato.class))).thenReturn(candidato);

        // Act
        Candidato salvo = candidatoService.criar(candidato);

        // Assert
        assertNotNull(salvo);
        verify(candidatoRepository, times(1)).save(candidato);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve listar todos os candidatos")
    void deveListarTodosCandidatos() {
        when(candidatoRepository.findAll()).thenReturn(List.of(new Candidato()));
        assertFalse(candidatoService.listar().isEmpty());
        verify(candidatoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve buscar candidato por ID")
    void deveBuscarCandidatoPorId() {
        Candidato candidato = new Candidato();
        candidato.setId(1L);
        when(candidatoRepository.findById(1L)).thenReturn(java.util.Optional.of(candidato));
        assertNotNull(candidatoService.buscarPorId(1L));
        verify(candidatoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve deletar candidato")
    void deveDeletarCandidato() {
        candidatoService.deletar(1L);
        verify(candidatoRepository, times(1)).deleteById(1L);
    }
}
