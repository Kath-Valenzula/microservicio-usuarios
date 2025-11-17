package cl.duoc.dsy2205.microservicio_usuarios.controller;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.dsy2205.microservicio_usuarios.entity.Usuario;
import cl.duoc.dsy2205.microservicio_usuarios.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        log.info("GET /api/usuarios");
        return ResponseEntity.ok(usuarioService.findAll());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Usuario> create(@Valid @RequestBody Usuario usuario) {
        log.info("POST /api/usuarios - creating usuario: {}", usuario.getNombre());
    Usuario nuevo = usuarioService.save(usuario);
    Long id = Objects.requireNonNull(nuevo.getIdUsuario(), "Created usuario id is null");
    URI location = Objects.requireNonNull(URI.create("/api/usuarios/" + id));
    return ResponseEntity.created(location).body(nuevo);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        return usuarioService.findById(id)
                .map(existing -> {
                    usuario.setIdUsuario(id);
                    return ResponseEntity.ok(usuarioService.save(usuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /api/usuarios/{}", id);
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
