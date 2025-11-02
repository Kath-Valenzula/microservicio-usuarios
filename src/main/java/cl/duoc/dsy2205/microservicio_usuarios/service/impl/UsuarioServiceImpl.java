package cl.duoc.dsy2205.microservicio_usuarios.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.duoc.dsy2205.microservicio_usuarios.entity.Usuario;
import cl.duoc.dsy2205.microservicio_usuarios.repository.UsuarioRepository;
import cl.duoc.dsy2205.microservicio_usuarios.service.UsuarioService;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario crear(Usuario nuevo) {
        return usuarioRepository.save(nuevo);
    }

    @Override
    public Optional<Usuario> actualizar(Long id, Usuario datosActualizados) {
        return usuarioRepository.findById(id).map(existing -> {
            existing.setNombreCompleto(datosActualizados.getNombreCompleto());
            existing.setEmail(datosActualizados.getEmail());
            existing.setPassword(datosActualizados.getPassword());
            existing.setRol(datosActualizados.getRol());
            return usuarioRepository.save(existing);
        });
    }

    @Override
    public boolean eliminar(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuarioRepository.delete(usuario);
            return true;
        }).orElse(false);
    }

    @Override
    public Optional<Usuario> login(String email, String password) {
        return usuarioRepository.findByEmailAndPassword(email, password);
    }
}