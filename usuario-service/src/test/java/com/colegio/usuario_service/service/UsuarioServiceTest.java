package com.colegio.usuario_service.service;

import com.colegio.usuario_service.entity.Usuario;
import com.colegio.usuario_service.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardar_ConPasswordSeEncripta() {
        Usuario u = new Usuario();
        u.setPassword("admin123");
        
        when(passwordEncoder.encode("admin123")).thenReturn("hash123");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(u);

        Usuario result = usuarioService.guardar(u);
        
        assertEquals("hash123", result.getPassword());
    }

    @Test
    void guardar_SinPasswordNoSeEncripta() {
        Usuario u = new Usuario();
        u.setPassword(""); 
        
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(u);
        
        Usuario result = usuarioService.guardar(u);
        
        assertEquals("", result.getPassword());
    }

    @Test
    void actualizar_Exitoso() {
        Usuario dbUser = new Usuario();
        dbUser.setPassword("viejaClave");
        
        Usuario newData = new Usuario();
        newData.setNombre("Nuevo Nombre");
        newData.setPassword("nuevaClave");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(dbUser));
        when(passwordEncoder.encode("nuevaClave")).thenReturn("nuevoHash");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(dbUser);

        Usuario result = usuarioService.actualizar(1L, newData);
        
        assertEquals("nuevoHash", result.getPassword());
        assertEquals("Nuevo Nombre", result.getNombre());
    }

    @Test
    void actualizar_UsuarioNoEncontrado_LanzaExcepcion() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> usuarioService.actualizar(1L, new Usuario()));
    }

    @Test
    void fallbackListarUsuarios_RetornaListaVacia() {
        List<Usuario> result = usuarioService.fallbackListarUsuarios(new RuntimeException("Simulación de caída CB"));
        
        assertTrue(result.isEmpty());
    }
}