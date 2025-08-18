package service;

import entity.Usuario;
import repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Usuario> listar() {
        return List.of();
    }

    public Usuario buscarPorId(Long id) {
        return null;
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        return usuario;
    }

    public void deletar(Long id) {
    }

    // outros métodos CRUD...
}