package app.projeto.service;

import app.projeto.entity.Candidato;
import app.projeto.repository.CandidatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    @DisplayName("TESTE DE UNIDADE – Deve lançar exceção ao tentar salvar candidato com CPF inválido")
    void deveLancarExcecaoQuandoCpfInvalido() {
        // Arrange
        Candidato candidato = new Candidato();
        candidato.setCpf("123"); // CPF inválido

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            candidatoService.salvar(candidato);
        });

        assertEquals("CPF inválido", exception.getMessage());
        verify(candidatoRepository, never()).save(any(Candidato.class));
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve salvar candidato com CPF válido")
    void deveSalvarCandidatoComCpfValido() {
        // Arrange
        Candidato candidato = new Candidato();
        candidato.setCpf("12345678909"); // CPF válido

        when(candidatoRepository.save(any(Candidato.class))).thenReturn(candidato);

        // Act
        Candidato salvo = candidatoService.salvar(candidato);

        // Assert
        assertNotNull(salvo);
        verify(candidatoRepository, times(1)).save(candidato);
    }
}
