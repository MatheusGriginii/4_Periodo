package service;

import app.projeto.entity.Usuario;
import app.projeto.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criar(Usuario usuario) {
        if (usuario.getVagas() == null || usuario.getVagas().isEmpty()) {
            throw new RuntimeException("Não é possível cadastrar usuário sem associar vaga");
        }
        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            usuario.setNome("INCOMPLETO");
        }
        return usuarioRepository.save(usuario);
    }

    // outros métodos CRUD...
}