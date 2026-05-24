package com.colegio.auth_service.controller;

import com.colegio.auth_service.client.UsuarioFeignClient;
import com.colegio.auth_service.dto.AuthUserDto;
import com.colegio.auth_service.dto.TokenDto;
import com.colegio.auth_service.dto.UsuarioDto;
import com.colegio.auth_service.security.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Importar esto
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Seguridad y Accesos", description = "Endpoints para el inicio de sesión")
public class AuthController {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UsuarioFeignClient usuarioFeignClient;

    // Usaremos un codificador instanciado manualmente para asegurar consistencia
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @Operation(summary = "Iniciar sesión")
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody AuthUserDto authUserDto) {
        
        System.out.println("DEBUG: --- INICIO LOGIN ---");
        
        try {
            UsuarioDto usuarioReal = usuarioFeignClient.buscarPorEmail(authUserDto.getEmail());

            if (usuarioReal == null) {
                System.out.println("DEBUG: Usuario no encontrado");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            boolean esValida = passwordEncoder.matches(authUserDto.getPassword(), usuarioReal.getPassword());
            
            System.out.println("DEBUG: Email: " + usuarioReal.getEmail());
            System.out.println("DEBUG: ¿Coinciden? " + esValida);

            if (esValida) {
                String token = jwtProvider.createToken(usuarioReal.getEmail(), usuarioReal.getRol());
                return ResponseEntity.ok(new TokenDto(token, usuarioReal.getRol(), usuarioReal.getId()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}