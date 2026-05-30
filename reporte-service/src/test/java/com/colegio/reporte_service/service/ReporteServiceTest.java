package com.colegio.reporte_service.service;

import com.colegio.reporte_service.client.EstudianteClient;
import com.colegio.reporte_service.client.EvaluacionClient;
import com.colegio.reporte_service.dto.ReporteGeneralDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReporteServiceTest {
    @Mock private EstudianteClient estudianteClient;
    @Mock private EvaluacionClient evaluacionClient;
    @InjectMocks private ReporteService reporteService;

    @Test
    void testObtenerReporteGeneral_Exito() {
        // Mockeamos respuesta de Estudiantes
        when(estudianteClient.listarTodos()).thenReturn(List.of(new Object(), new Object()));
        
        // Mockeamos respuesta de Evaluaciones
        when(evaluacionClient.listarTodas()).thenReturn(List.of(Map.of("nota", "6.0")));
        
        ReporteGeneralDto reporte = reporteService.obtenerReporteGeneral();
        
        assertEquals(2, reporte.getTotalEstudiantes());
        assertEquals(6.0, reporte.getPromedioGeneralColegio());
    }
}