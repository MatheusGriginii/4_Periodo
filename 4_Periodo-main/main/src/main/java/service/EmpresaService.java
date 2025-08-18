package service;

import entity.Empresa;
import repository.EmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public List<Empresa> listar() {
        return empresaRepository.findAll();
    }

    public Empresa buscarPorId(Long id) {
        return empresaRepository.findById(id).orElseThrow();
    }

    public Empresa criar(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public Empresa atualizar(Long id, Empresa empresa) {
        empresa.setId(id);
        return empresaRepository.save(empresa);
    }

    public void deletar(Long id) {
        empresaRepository.deleteById(id);
    }
}