package com.colegio.usuario_service.service;

import com.colegio.usuario_service.entity.Usuario;
import com.colegio.usuario_service.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder; 
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder; 

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioMock;

    @BeforeEach
    void setUp() {
        usuarioMock = new Usuario("12.345.678-9", "Profesor Marcelo", "marcelo@colegio.com", "123456", "PROFESOR");
        usuarioMock.setId(2L);
    }

    @Test
    void testObtenerTodos_DebeRetornarLista() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuarioMock));
        
        List<Usuario> resultado = usuarioService.obtenerTodos();
        
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Profesor Marcelo", resultado.get(0).getNombre());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPorId_Exito() {
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuarioMock));
        
        Optional<Usuario> resultado = usuarioService.obtenerPorId(2L);
        
        assertTrue(resultado.isPresent());
        assertEquals("marcelo@colegio.com", resultado.get().getEmail());
        verify(usuarioRepository, times(1)).findById(2L);
    }

    @Test
    void testObtenerPorId_NoEncontrado() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());
        
        Optional<Usuario> resultado = usuarioService.obtenerPorId(99L);
        
        assertFalse(resultado.isPresent());
        verify(usuarioRepository, times(1)).findById(99L);
    }

    @Test
    void testGuardarUsuario_Exito() {
        // Le decimos a Mockito que cuando encripte, devuelva un texto mock cualquiera
        when(passwordEncoder.encode(anyString())).thenReturn("password_encriptado_mock");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMock);
        
        Usuario resultado = usuarioService.guardar(usuarioMock);
        
        assertNotNull(resultado);
        assertEquals("Profesor Marcelo", resultado.getNombre());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testEliminarUsuario_Exito() {
        doNothing().when(usuarioRepository).deleteById(2L);
        
        usuarioService.eliminar(2L);
        
        verify(usuarioRepository, times(1)).deleteById(2L);
    }
}