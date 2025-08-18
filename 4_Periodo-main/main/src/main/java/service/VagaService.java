package service;

import entity.Vaga;
import repository.VagaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VagaService {
    private final VagaRepository vagaRepository;

    public VagaService(VagaRepository vagaRepository) {
        this.vagaRepository = vagaRepository;
    }

    public List<Vaga> listar() {
        return vagaRepository.findAll();
    }

    public Vaga buscarPorId(Long id) {
        return vagaRepository.findById(id).orElseThrow();
    }

    public Vaga criar(Vaga vaga) {
        if (vaga.getEmpresa() == null) {
            throw new RuntimeException("Não é possível cadastrar vaga sem empresa associada.");
        }
        return vagaRepository.save(vaga);
    }

    public Vaga atualizar(Long id, Vaga vaga) {
        vaga.setId(id);
        return vagaRepository.save(vaga);
    }

    public void deletar(Long id) {
        vagaRepository.deleteById(id);
    }
}