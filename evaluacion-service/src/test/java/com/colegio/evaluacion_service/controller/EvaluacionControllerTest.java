package com.colegio.evaluacion_service.controller;

import com.colegio.evaluacion_service.entity.Evaluacion;
import com.colegio.evaluacion_service.service.EvaluacionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EvaluacionControllerTest {
    @Mock private EvaluacionService service;
    @InjectMocks private EvaluacionController controller;

    @Test
    void testListarTodas() {
        when(service.listarTodas()).thenReturn(List.of(new Evaluacion()));
        assertEquals(HttpStatus.OK, controller.listarTodas().getStatusCode());
    }

    @Test
    void testGuardar() {
        Evaluacion e = new Evaluacion();
        when(service.guardar(any())).thenReturn(e);
        assertEquals(HttpStatus.CREATED, controller.guardar(e).getStatusCode());
    }
}