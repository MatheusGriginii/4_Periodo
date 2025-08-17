package controller;

import app.projeto.entity.Vaga;
import app.projeto.service.VagaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vagas")
public class VagaController {
    private final VagaService vagaService;

    public VagaController(VagaService vagaService) {
        this.vagaService = vagaService;
    }

    @GetMapping
    public List<Vaga> listar() {
        return vagaService.listar();
    }

    @GetMapping("/{id}")
    public Vaga buscarPorId(@PathVariable Long id) {
        return vagaService.buscarPorId(id);
    }

    @PostMapping
    public Vaga criar(@RequestBody Vaga vaga) {
        return vagaService.criar(vaga);
    }

    @PutMapping("/{id}")
    public Vaga atualizar(@PathVariable Long id, @RequestBody Vaga vaga) {
        return vagaService.atualizar(id, vaga);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        vagaService.deletar(id);
    }
}