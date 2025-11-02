package cl.duoc.dsy2205.microservicio_usuarios.service;

import java.util.List;
import java.util.Optional;

import cl.duoc.dsy2205.microservicio_usuarios.entity.Usuario;

public interface UsuarioService {

    List<Usuario> listarTodos();

    Optional<Usuario> buscarPorId(Long id);

    Usuario crear(Usuario nuevo);

    Optional<Usuario> actualizar(Long id, Usuario datosActualizados);

    boolean eliminar(Long id);

    Optional<Usuario> login(String email, String password);
}