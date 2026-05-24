package com.colegio.academico_service.controller;

import com.colegio.academico_service.entity.Asignatura;
import com.colegio.academico_service.service.AsignaturaService;
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
public class AsignaturaControllerTest {

    @Mock
    private AsignaturaService asignaturaService;

    @InjectMocks
    private AsignaturaController asignaturaController;

    private Asignatura asignaturaMock;

    @BeforeEach
    void setUp() {
        asignaturaMock = new Asignatura();
    }

    @Test
    void testListarAsignaturas() {
        when(asignaturaService.listarTodas()).thenReturn(List.of(asignaturaMock));

        ResponseEntity<List<Asignatura>> response = asignaturaController.listarAsignaturas();

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(asignaturaService, times(1)).listarTodas();
    }

    @Test
    void testListarPorCurso() {
        when(asignaturaService.listarPorCurso(1L)).thenReturn(List.of(asignaturaMock));

        ResponseEntity<List<Asignatura>> response = asignaturaController.listarPorCurso(1L);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        verify(asignaturaService, times(1)).listarPorCurso(1L);
    }

    @Test
    void testCrearAsignatura() {
        when(asignaturaService.guardar(any(Asignatura.class))).thenReturn(asignaturaMock);

        ResponseEntity<Asignatura> response = asignaturaController.crearAsignatura(asignaturaMock);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        verify(asignaturaService, times(1)).guardar(any(Asignatura.class));
    }
}