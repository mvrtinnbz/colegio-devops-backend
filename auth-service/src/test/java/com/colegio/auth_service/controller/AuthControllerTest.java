package com.colegio.auth_service.controller;

import com.colegio.auth_service.client.UsuarioFeignClient;
import com.colegio.auth_service.dto.AuthUserDto;
import com.colegio.auth_service.dto.TokenDto;
import com.colegio.auth_service.dto.UsuarioDto;
import com.colegio.auth_service.security.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private UsuarioFeignClient usuarioFeignClient;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_CredencialesCorrectas() {
        AuthUserDto req = new AuthUserDto();
        req.setEmail("admin@test.com");
        req.setPassword("123456");

        UsuarioDto mockUser = new UsuarioDto("admin@test.com", "hashDB", "ADMIN", "Juan");
        mockUser.setId(1L);

        when(usuarioFeignClient.buscarPorEmail(anyString())).thenReturn(mockUser);
        when(passwordEncoder.matches("123456", "hashDB")).thenReturn(true);
        when(jwtProvider.createToken("admin@test.com", "ADMIN")).thenReturn("tokenSuperSeguro");

        ResponseEntity<TokenDto> response = authController.login(req);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("tokenSuperSeguro", response.getBody().getToken());
    }

    @Test
    void login_UsuarioNoExiste() {
        AuthUserDto req = new AuthUserDto();
        req.setEmail("fantasma@test.com");

        when(usuarioFeignClient.buscarPorEmail(anyString())).thenReturn(null);

        ResponseEntity<TokenDto> response = authController.login(req);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void login_ContrasenaIncorrecta() {
        AuthUserDto req = new AuthUserDto();
        req.setEmail("admin@test.com");
        req.setPassword("claveMala");

        UsuarioDto mockUser = new UsuarioDto("admin@test.com", "hashDB", "ADMIN", "Juan");

        when(usuarioFeignClient.buscarPorEmail(anyString())).thenReturn(mockUser);
        when(passwordEncoder.matches("claveMala", "hashDB")).thenReturn(false);

        ResponseEntity<TokenDto> response = authController.login(req);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void login_FallaServidor() {
        AuthUserDto req = new AuthUserDto();
        req.setEmail("error@test.com");

        when(usuarioFeignClient.buscarPorEmail(anyString())).thenThrow(new RuntimeException("Caída Feign"));

        ResponseEntity<TokenDto> response = authController.login(req);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}