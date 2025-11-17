package cl.duoc.dsy2205.microservicio_usuarios.controller;

import java.time.LocalDate;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.duoc.dsy2205.microservicio_usuarios.entity.Usuario;
import cl.duoc.dsy2205.microservicio_usuarios.service.UsuarioService;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
@SuppressWarnings({"removal", "nullness"})
public class UsuarioControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @MockBean UsuarioService service;

    @Test
    void createHappyPath() throws Exception {
        Usuario input = new Usuario();
        input.setNombre("Juan");
        input.setApellido("Perez");
        input.setCorreo("juan.perez@example.com");
        input.setTelefono("+56912345678");
        input.setFechaRegistro(LocalDate.now());

        Usuario created = new Usuario();
        created.setIdUsuario(1L);
        created.setNombre(input.getNombre());
        created.setApellido(input.getApellido());
        created.setCorreo(input.getCorreo());
        created.setTelefono(input.getTelefono());
        created.setFechaRegistro(input.getFechaRegistro());

        when(service.save(any(Usuario.class))).thenReturn(created);

    mvc.perform(post("/api/usuarios")
            .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
            .content(Objects.requireNonNull(mapper.writeValueAsString(input))))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/usuarios/1"));
    }

    @Test
    void createNullIdFromService_shouldReturnServerError() throws Exception {
        Usuario input = new Usuario();
        input.setNombre("Ana");
        input.setApellido("Gonzalez");
        input.setCorreo("ana.g@example.com");
        when(service.save(any(Usuario.class))).thenReturn(new Usuario()); // id null

    mvc.perform(post("/api/usuarios")
            .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
            .content(Objects.requireNonNull(mapper.writeValueAsString(input))))
                .andExpect(status().is5xxServerError());
    }
}
