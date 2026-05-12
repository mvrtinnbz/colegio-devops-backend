package com.colegio.usuario_service.service;

import com.colegio.usuario_service.entity.Usuario;
import com.colegio.usuario_service.repository.UsuarioRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Importación para cifrar contraseñas
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyectamos el encriptador

    // Aplicamos Circuit Breaker: si falla mucho, se activa el método 'fallback'
    @CircuitBreaker(name = "usuarioServiceCB", fallbackMethod = "fallbackListarUsuarios")
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    // --- MÉTODO MODIFICADO: Ahora encripta al crear ---
    public Usuario guardar(Usuario usuario) {
        // Verificamos si la contraseña viene en el JSON y la encriptamos antes de guardarla
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return usuarioRepository.save(usuario);
    }

    // Método de respaldo (Fallback) para cumplir con la Resiliencia de la rúbrica
    public List<Usuario> fallbackListarUsuarios(Exception e) {
        System.out.println("Circuit Breaker activado: El servicio de base de datos no responde.");
        return List.of(); // Retorna lista vacía en lugar de un error 500
    }

    // Buscar por ID
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Buscar por Email (NUEVO MÉTODO)
    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Eliminar
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Actualizar (Lógica sin modificar el RUT)
    public Usuario actualizar(Long id, Usuario usuarioDetalles) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioDetalles.getNombre());
            usuario.setEmail(usuarioDetalles.getEmail());
            usuario.setRol(usuarioDetalles.getRol());
            // Eliminamos la actualización del RUT por seguridad

            // Verificamos si hay una contraseña nueva y la encriptamos
            if (usuarioDetalles.getPassword() != null && !usuarioDetalles.getPassword().isEmpty()) {
                usuario.setPassword(passwordEncoder.encode(usuarioDetalles.getPassword()));
            }

            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}