package cl.duoc.dsy2205.microservicio_usuarios.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import cl.duoc.dsy2205.microservicio_usuarios.entity.Usuario;
import cl.duoc.dsy2205.microservicio_usuarios.exception.IntegrityViolationException;
import cl.duoc.dsy2205.microservicio_usuarios.repository.UsuarioRepository;
import cl.duoc.dsy2205.microservicio_usuarios.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        Objects.requireNonNull(id, "id debe no ser null");
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        Objects.requireNonNull(usuario, "usuario debe no ser null");
        return Objects.requireNonNull(usuarioRepository.save(usuario));
    }

    @Override
    public void deleteById(Long id) {
        Objects.requireNonNull(id, "id debe no ser null");
        try {
            usuarioRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            log.warn("Attempt to delete usuario {} failed due to integrity constraints", id);
            throw new IntegrityViolationException("No se puede eliminar el usuario porque tiene referencias en la base de datos");
        }
    }
}
