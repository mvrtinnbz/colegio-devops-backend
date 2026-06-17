package com.colegio.reporte_service.service;

import com.colegio.reporte_service.client.EstudianteClient;
import com.colegio.reporte_service.client.EvaluacionClient;
import com.colegio.reporte_service.dto.ReporteGeneralDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReporteServiceTest {

    @Mock
    private EstudianteClient estudianteClient;

    @Mock
    private EvaluacionClient evaluacionClient;

    @InjectMocks
    private ReporteService reporteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerReporteGeneral_CaminoFeliz() {
        // Simulamos que ambos clientes responden bien
        when(estudianteClient.listarTodos()).thenReturn(List.of(new Object(), new Object()));
        when(evaluacionClient.listarTodas()).thenReturn(List.of(
                Map.of("nota", 6.0),
                Map.of("nota", 7.0)
        ));

        ReporteGeneralDto reporte = reporteService.obtenerReporteGeneral();

        assertEquals(2, reporte.getTotalEstudiantes());
        assertEquals(6.5, reporte.getPromedioGeneralColegio());
        assertTrue(reporte.getEstado().contains("sincronizado"));
    }

    @Test
    void obtenerReporteGeneral_FallaEstudianteClient() {
        // Simulamos que el microservicio de estudiantes se cae
        when(estudianteClient.listarTodos()).thenThrow(new RuntimeException("Error Feign"));
        when(evaluacionClient.listarTodas()).thenReturn(List.of(Map.of("nota", 5.0)));

        ReporteGeneralDto reporte = reporteService.obtenerReporteGeneral();

        assertEquals(0, reporte.getTotalEstudiantes());
        assertEquals(5.0, reporte.getPromedioGeneralColegio());
        assertTrue(reporte.getEstado().contains("Estudiante-Service no responde"));
    }

    @Test
    void obtenerReporteGeneral_FallaEvaluacionClient() {
        // Simulamos que el microservicio de evaluaciones se cae
        when(estudianteClient.listarTodos()).thenReturn(List.of(new Object()));
        when(evaluacionClient.listarTodas()).thenThrow(new RuntimeException("Error Feign"));

        ReporteGeneralDto reporte = reporteService.obtenerReporteGeneral();

        assertEquals(1, reporte.getTotalEstudiantes());
        assertEquals(0.0, reporte.getPromedioGeneralColegio());
        assertTrue(reporte.getEstado().contains("Evaluacion-Service no responde"));
    }

    @Test
    void obtenerReporteGeneral_SinNotas() {
        // Simulamos que responde bien, pero la lista de notas está vacía
        when(estudianteClient.listarTodos()).thenReturn(List.of(new Object()));
        when(evaluacionClient.listarTodas()).thenReturn(Collections.emptyList());

        ReporteGeneralDto reporte = reporteService.obtenerReporteGeneral();

        assertEquals(1, reporte.getTotalEstudiantes());
        assertEquals(0.0, reporte.getPromedioGeneralColegio());
    }
}