package cl.duoc.dsy2205.microservicio_usuarios.service;

import cl.duoc.dsy2205.microservicio_usuarios.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> findAll();

    Optional<Usuario> findById(Long id);

    Usuario save(Usuario usuario);

    void deleteById(Long id);
}
