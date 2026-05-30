package com.colegio.asistencia_service.controller;

import com.colegio.asistencia_service.entity.Asistencia;
import com.colegio.asistencia_service.service.AsistenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AsistenciaControllerTest {

    @Mock
    private AsistenciaService asistenciaService;

    @InjectMocks
    private AsistenciaController asistenciaController;

    private Asistencia asistenciaMock;

    @BeforeEach
    void setUp() {
        asistenciaMock = new Asistencia();
    }

    @Test
    void testListarTodas() {
        when(asistenciaService.listarTodas()).thenReturn(List.of(asistenciaMock));

        ResponseEntity<List<Asistencia>> response = asistenciaController.listarTodas();

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(asistenciaService, times(1)).listarTodas();
    }

    @Test
    void testGuardar() {
        when(asistenciaService.guardar(any(Asistencia.class))).thenReturn(asistenciaMock);

        ResponseEntity<Asistencia> response = asistenciaController.guardar(asistenciaMock);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        verify(asistenciaService, times(1)).guardar(any(Asistencia.class));
    }
}