package cl.duoc.dsy2205.microservicio_usuarios.service.impl;

import cl.duoc.dsy2205.microservicio_usuarios.entity.Usuario;
import cl.duoc.dsy2205.microservicio_usuarios.exception.IntegrityViolationException;
import cl.duoc.dsy2205.microservicio_usuarios.repository.UsuarioRepository;
import cl.duoc.dsy2205.microservicio_usuarios.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteById(Long id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            log.warn("Attempt to delete usuario {} failed due to integrity constraints", id);
            throw new IntegrityViolationException("No se puede eliminar el usuario porque tiene referencias en la base de datos");
        }
    }
}
