package controller;

import app.projeto.entity.Candidato;
import app.projeto.service.CandidatoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidatos")
public class CandidatoController {
    private final CandidatoService candidatoService;

    public CandidatoController(CandidatoService candidatoService) {
        this.candidatoService = candidatoService;
    }

    @GetMapping
    public List<Candidato> listar() {
        return candidatoService.listar();
    }

    @GetMapping("/{id}")
    public Candidato buscarPorId(@PathVariable Long id) {
        return candidatoService.buscarPorId(id);
    }

    @PostMapping
    public Candidato criar(@RequestBody Candidato candidato) {
        return candidatoService.criar(candidato);
    }

    @PutMapping("/{id}")
    public Candidato atualizar(@PathVariable Long id, @RequestBody Candidato candidato) {
        return candidatoService.atualizar(id, candidato);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        candidatoService.deletar(id);
    }
}