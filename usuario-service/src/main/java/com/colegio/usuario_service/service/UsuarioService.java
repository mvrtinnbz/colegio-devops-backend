package com.colegio.usuario_service.service;

import com.colegio.usuario_service.entity.Usuario;
import com.colegio.usuario_service.repository.UsuarioRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyectamos el encriptador

    @CircuitBreaker(name = "usuarioServiceCB", fallbackMethod = "fallbackListarUsuarios")
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario guardar(Usuario usuario) {
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> fallbackListarUsuarios(Exception e) {
        System.out.println("Circuit Breaker activado: El servicio de base de datos no responde.");
        return List.of();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario actualizar(Long id, Usuario usuarioDetalles) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioDetalles.getNombre());
            usuario.setEmail(usuarioDetalles.getEmail());
            usuario.setRol(usuarioDetalles.getRol());


            if (usuarioDetalles.getPassword() != null && !usuarioDetalles.getPassword().isEmpty()) {
                usuario.setPassword(passwordEncoder.encode(usuarioDetalles.getPassword()));
            }

            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}