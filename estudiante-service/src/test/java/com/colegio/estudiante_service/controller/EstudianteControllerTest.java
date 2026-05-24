package com.colegio.estudiante_service.controller;

import com.colegio.estudiante_service.entity.Estudiante;
import com.colegio.estudiante_service.service.EstudianteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstudianteControllerTest {
    @Mock private EstudianteService service;
    @InjectMocks private EstudianteController controller;

    @Test
    void testMatricular() {
        when(service.registrarEstudiante(any())).thenReturn(new Estudiante());
        assertEquals(HttpStatus.CREATED, controller.matricularEstudiante(new Estudiante()).getStatusCode());
    }

    @Test
    void testObtener() {
        when(service.obtenerPorId(1L)).thenReturn(Optional.of(new Estudiante()));
        assertEquals(HttpStatus.OK, controller.obtenerEstudiante(1L).getStatusCode());
    }

    @Test
    void testEliminar() {
        assertEquals(HttpStatus.NO_CONTENT, controller.eliminarEstudiante(1L).getStatusCode());
    }
}