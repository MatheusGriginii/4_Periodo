package app.projeto.controller;

import app.projeto.entity.Candidato;
import app.projeto.service.CandidatoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/buscar")
    public List<Candidato> buscarPorNome(@RequestParam String nome) {
        return candidatoService.buscarPorNome(nome);
    }

    @GetMapping("/por-titulo")
    public List<Candidato> buscarPorTituloVaga(@RequestParam String titulo) {
        return candidatoService.buscarPorTituloVaga(titulo);
    }

    @PostMapping
    public ResponseEntity<Candidato> criar(@Valid @RequestBody Candidato candidato) {
        Candidato candidatoCriado = candidatoService.criar(candidato);
        return ResponseEntity.status(HttpStatus.CREATED).body(candidatoCriado);
    }

    @PutMapping("/{id}")
    public Candidato atualizar(@PathVariable Long id, @Valid @RequestBody Candidato candidato) {
        return candidatoService.atualizar(id, candidato);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        candidatoService.deletar(id);
    }
}
