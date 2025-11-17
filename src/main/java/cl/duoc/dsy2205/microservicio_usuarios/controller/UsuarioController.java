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

import cl.duoc.dsy2205.microservicio_usuarios.dto.UsuarioDTO;
import cl.duoc.dsy2205.microservicio_usuarios.entity.Usuario;
import cl.duoc.dsy2205.microservicio_usuarios.mapper.UsuarioMapper;
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
    public ResponseEntity<List<UsuarioDTO>> getAll() {
        log.info("GET /api/usuarios");
        var list = usuarioService.findAll();
        var dtoList = list.stream().map(UsuarioMapper::toDto).toList();
        return ResponseEntity.ok(dtoList);
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getById(@PathVariable Long id) {
        Usuario u = usuarioService.findById(id).orElse(null);
        return ResponseEntity.ok(UsuarioMapper.toDto(u));
    }

    // CREATE
    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioDTO usuarioDto) {
        Usuario usuario = UsuarioMapper.toEntity(usuarioDto);
        log.info("POST /api/usuarios - creating usuario: {}", usuario.getNombre());
        Usuario nuevo = usuarioService.save(usuario);
        Long id = Objects.requireNonNull(nuevo.getIdUsuario(), "Created usuario id is null");
        URI location = Objects.requireNonNull(URI.create("/api/usuarios/" + id));
        return ResponseEntity.created(location).body(UsuarioMapper.toDto(nuevo));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuarioDto) {
        // Validar existencia mediante el servicio (lanzará ResourceNotFoundException si no existe)
        usuarioService.findById(id);
        Usuario usuario = UsuarioMapper.toEntity(usuarioDto);
        usuario.setIdUsuario(id);
        Usuario updated = usuarioService.save(usuario);
        return ResponseEntity.ok(UsuarioMapper.toDto(updated));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /api/usuarios/{}", id);
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
