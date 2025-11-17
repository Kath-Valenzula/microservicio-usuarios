package cl.duoc.dsy2205.microservicio_usuarios.mapper;

import cl.duoc.dsy2205.microservicio_usuarios.dto.UsuarioDTO;
import cl.duoc.dsy2205.microservicio_usuarios.entity.Usuario;

public final class UsuarioMapper {

    private UsuarioMapper() {}

    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;
        Usuario u = new Usuario();
        u.setIdUsuario(dto.getIdUsuario());
        u.setNombre(dto.getNombre());
        u.setApellido(dto.getApellido());
        u.setCorreo(dto.getCorreo());
        u.setTelefono(dto.getTelefono());
        u.setFechaRegistro(dto.getFechaRegistro());
        return u;
    }

    public static UsuarioDTO toDto(Usuario u) {
        if (u == null) return null;
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(u.getIdUsuario());
        dto.setNombre(u.getNombre());
        dto.setApellido(u.getApellido());
        dto.setCorreo(u.getCorreo());
        dto.setTelefono(u.getTelefono());
        dto.setFechaRegistro(u.getFechaRegistro());
        return dto;
    }
}
