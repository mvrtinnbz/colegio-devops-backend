package com.colegio.auth_service.controller;

import com.colegio.auth_service.client.UsuarioFeignClient;
import com.colegio.auth_service.dto.AuthUserDto;
import com.colegio.auth_service.dto.TokenDto;
import com.colegio.auth_service.dto.UsuarioDto;
import com.colegio.auth_service.security.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private UsuarioFeignClient usuarioFeignClient;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    private AuthUserDto authUserDtoMock;
    private UsuarioDto usuarioDtoMock;

    @BeforeEach
    void setUp() {
        // Disfrazamos los DTOs para no depender de sus constructores
        authUserDtoMock = mock(AuthUserDto.class);
        usuarioDtoMock = mock(UsuarioDto.class);
    }

    @Test
    void testLogin_CamposNulos() {
        // Caso: Viene el email vacío
        when(authUserDtoMock.getEmail()).thenReturn(null);

        ResponseEntity<TokenDto> response = authController.login(authUserDtoMock);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());
    }

    @Test
    void testLogin_UsuarioNoExiste() {
        // Caso: Pasa la validación, pero Feign no encuentra al usuario
        when(authUserDtoMock.getEmail()).thenReturn("admin@colegio.com");
        when(authUserDtoMock.getPassword()).thenReturn("123456");
        when(usuarioFeignClient.buscarPorEmail(anyString())).thenReturn(null);

        ResponseEntity<TokenDto> response = authController.login(authUserDtoMock);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode().value());
    }

    @Test
    void testLogin_ContrasenaIncorrecta() {
        // Caso: El usuario existe, pero la clave encriptada no coincide
        when(authUserDtoMock.getEmail()).thenReturn("admin@colegio.com");
        when(authUserDtoMock.getPassword()).thenReturn("claveMala");

        when(usuarioDtoMock.getPassword()).thenReturn("hashBaseDeDatos");
        when(usuarioFeignClient.buscarPorEmail(anyString())).thenReturn(usuarioDtoMock);

        // Simulamos que el validador rechaza la contraseña
        when(passwordEncoder.matches("claveMala", "hashBaseDeDatos")).thenReturn(false);

        ResponseEntity<TokenDto> response = authController.login(authUserDtoMock);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode().value());
    }

    @Test
    void testLogin_Exito() {
        // Caso Feliz: Todo coincide y se genera el Token
        when(authUserDtoMock.getEmail()).thenReturn("admin@colegio.com");
        when(authUserDtoMock.getPassword()).thenReturn("123456");

        when(usuarioDtoMock.getId()).thenReturn(1L);
        when(usuarioDtoMock.getEmail()).thenReturn("admin@colegio.com");
        when(usuarioDtoMock.getPassword()).thenReturn("hashBaseDeDatos");
        when(usuarioDtoMock.getRol()).thenReturn("ADMIN");

        when(usuarioFeignClient.buscarPorEmail("admin@colegio.com")).thenReturn(usuarioDtoMock);
        when(passwordEncoder.matches("123456", "hashBaseDeDatos")).thenReturn(true);
        when(jwtProvider.createToken("admin@colegio.com", "ADMIN")).thenReturn("token_jwt_falso_123");

        ResponseEntity<TokenDto> response = authController.login(authUserDtoMock);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void testLogin_ExcepcionInesperada() {
        // Caso: Falla el servicio externo o hay un null pointer feo
        when(authUserDtoMock.getEmail()).thenReturn("admin@colegio.com");
        when(authUserDtoMock.getPassword()).thenReturn("123456");
        
        // Forzamos un error en Feign
        when(usuarioFeignClient.buscarPorEmail(anyString())).thenThrow(new RuntimeException("Microservicio Caído"));

        ResponseEntity<TokenDto> response = authController.login(authUserDtoMock);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode().value());
    }
}