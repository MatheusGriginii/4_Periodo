package app.projeto.service;

import app.projeto.entity.Empresa;
import app.projeto.repository.EmpresaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("TESTES DE UNIDADE – EmpresaService")
public class EmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private EmpresaService empresaService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve lançar exceção ao criar empresa sem nome")
    void deveLancarExcecaoQuandoNomeVazio() {
        Empresa empresa = new Empresa();
        empresa.setNome("");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            empresaService.criar(empresa);
        });

        assertEquals("Nome da empresa é obrigatório", exception.getMessage());
        verify(empresaRepository, never()).save(any(Empresa.class));
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve criar empresa com nome válido")
    void deveCriarEmpresaComNomeValido() {
        Empresa empresa = new Empresa();
        empresa.setNome("Empresa Teste");

        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresa);

        Empresa salva = empresaService.criar(empresa);

        assertNotNull(salva);
        verify(empresaRepository, times(1)).save(empresa);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve listar todas as empresas")
    void deveListarTodasEmpresas() {
        when(empresaRepository.findAll()).thenReturn(List.of(new Empresa()));
        assertFalse(empresaService.listar().isEmpty());
        verify(empresaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve buscar empresa por ID")
    void deveBuscarEmpresaPorId() {
        Empresa empresa = new Empresa();
        empresa.setId(1L);
        when(empresaRepository.findById(1L)).thenReturn(java.util.Optional.of(empresa));
        assertNotNull(empresaService.buscarPorId(1L));
        verify(empresaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve deletar empresa")
    void deveDeletarEmpresa() {
        empresaService.deletar(1L);
        verify(empresaRepository, times(1)).deleteById(1L);
    }
}
