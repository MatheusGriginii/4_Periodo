package app.projeto.service;

import app.projeto.entity.Empresa;
import app.projeto.entity.Vaga;
import app.projeto.repository.VagaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("TESTES DE UNIDADE – VagaService")
public class VagaServiceTest {

    @Mock
    private VagaRepository vagaRepository;

    @InjectMocks
    private VagaService vagaService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve lançar exceção ao criar vaga sem empresa")
    void deveLancarExcecaoQuandoEmpresaNula() {
        Vaga vaga = new Vaga();
        vaga.setTitulo("Desenvolvedor Java");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            vagaService.criar(vaga);
        });

        assertEquals("Não é possível cadastrar vaga sem empresa associada.", exception.getMessage());
        verify(vagaRepository, never()).save(any(Vaga.class));
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve criar vaga com empresa válida")
    void deveCriarVagaComEmpresaValida() {
        Vaga vaga = new Vaga();
        vaga.setTitulo("Desenvolvedor Java");
        Empresa empresa = new Empresa();
        empresa.setNome("Empresa Teste");
        vaga.setEmpresa(empresa);

        when(vagaRepository.save(any(Vaga.class))).thenReturn(vaga);

        Vaga salva = vagaService.criar(vaga);

        assertNotNull(salva);
        verify(vagaRepository, times(1)).save(vaga);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve listar todas as vagas")
    void deveListarTodasVagas() {
        when(vagaRepository.findAll()).thenReturn(List.of(new Vaga()));
        assertFalse(vagaService.listar().isEmpty());
        verify(vagaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve buscar vaga por ID")
    void deveBuscarVagaPorId() {
        Vaga vaga = new Vaga();
        vaga.setId(1L);
        when(vagaRepository.findById(1L)).thenReturn(java.util.Optional.of(vaga));
        assertNotNull(vagaService.buscarPorId(1L));
        verify(vagaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve deletar vaga")
    void deveDeletarVaga() {
        vagaService.deletar(1L);
        verify(vagaRepository, times(1)).deleteById(1L);
    }
}
