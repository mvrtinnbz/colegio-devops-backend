package com.colegio.anotacion_service.controller;

import com.colegio.anotacion_service.entity.Anotacion;
import com.colegio.anotacion_service.service.AnotacionService;
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
public class AnotacionControllerTest {

    @Mock
    private AnotacionService anotacionService;

    @InjectMocks
    private AnotacionController anotacionController;

    private Anotacion anotacionMock;

    @BeforeEach
    void setUp() {
        anotacionMock = new Anotacion();
    }

    @Test
    void testListarTodas() {
        when(anotacionService.listarTodas()).thenReturn(List.of(anotacionMock));

        ResponseEntity<List<Anotacion>> response = anotacionController.listarTodas();

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(anotacionService, times(1)).listarTodas();
    }

    @Test
    void testListarPorEstudiante() {
        when(anotacionService.listarPorEstudiante(1L)).thenReturn(List.of(anotacionMock));

        ResponseEntity<List<Anotacion>> response = anotacionController.listarPorEstudiante(1L);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(anotacionService, times(1)).listarPorEstudiante(1L);
    }

    @Test
    void testGuardar() {
        when(anotacionService.guardar(any(Anotacion.class))).thenReturn(anotacionMock);

        ResponseEntity<Anotacion> response = anotacionController.guardar(anotacionMock);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        verify(anotacionService, times(1)).guardar(any(Anotacion.class));
    }
}