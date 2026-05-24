package com.colegio.usuario_service.controller;

import com.colegio.usuario_service.entity.Usuario;
import com.colegio.usuario_service.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuarioMock;

    @BeforeEach
    void setUp() {
        usuarioMock = new Usuario("12.345.678-9", "Profesor Marcelo", "marcelo@colegio.com", "123456", "PROFESOR");
        usuarioMock.setId(2L);
    }

    @Test
    void testListar() {
        when(usuarioService.obtenerTodos()).thenReturn(List.of(usuarioMock));

        // En tu controlador, este método devuelve List directo
        List<Usuario> response = usuarioController.listar();

        assertNotNull(response);
        assertEquals(1, response.size());
        verify(usuarioService, times(1)).obtenerTodos();
    }

    @Test
    void testCrear() {
        when(usuarioService.guardar(any(Usuario.class))).thenReturn(usuarioMock);

        ResponseEntity<Usuario> response = usuarioController.crear(usuarioMock);

        // Tu controlador devuelve 201 CREATED
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
        assertEquals("marcelo@colegio.com", response.getBody().getEmail());
        verify(usuarioService, times(1)).guardar(any(Usuario.class));
    }

    @Test
    void testObtenerPorId() {
        when(usuarioService.obtenerPorId(2L)).thenReturn(Optional.of(usuarioMock));

        ResponseEntity<Usuario> response = usuarioController.obtenerPorId(2L);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals("Profesor Marcelo", response.getBody().getNombre());
        verify(usuarioService, times(1)).obtenerPorId(2L);
    }

    @Test
    void testBuscarPorEmail() {
        // Ojo: tu controlador llama a obtenerPorEmail en el service
        when(usuarioService.obtenerPorEmail(anyString())).thenReturn(Optional.of(usuarioMock));

        ResponseEntity<Usuario> response = usuarioController.buscarPorEmail("marcelo@colegio.com");

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals("PROFESOR", response.getBody().getRol());
        verify(usuarioService, times(1)).obtenerPorEmail(anyString());
    }

    @Test
    void testActualizar() {
        when(usuarioService.actualizar(eq(2L), any(Usuario.class))).thenReturn(usuarioMock);

        ResponseEntity<Usuario> response = usuarioController.actualizar(2L, usuarioMock);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        verify(usuarioService, times(1)).actualizar(eq(2L), any(Usuario.class));
    }

    @Test
    void testEliminar() {
        doNothing().when(usuarioService).eliminar(2L);

        ResponseEntity<Void> response = usuarioController.eliminar(2L);

        // Tu controlador devuelve 204 NO CONTENT
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode().value());
        verify(usuarioService, times(1)).eliminar(2L);
    }
}