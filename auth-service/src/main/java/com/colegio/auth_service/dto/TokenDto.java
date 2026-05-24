package com.colegio.auth_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta exitosa que contiene la llave de acceso y los datos del usuario")
public class TokenDto {

    private String token;
    private String rol; 
    private Long id;
    private String nombre;

    // Constructor completo
    public TokenDto(String token, String rol, Long id, String nombre) {
        this.token = token;
        this.rol = rol;
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}