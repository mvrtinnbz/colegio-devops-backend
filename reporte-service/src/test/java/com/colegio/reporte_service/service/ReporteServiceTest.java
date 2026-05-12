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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReporteServiceTest {

    @Mock
    private EstudianteClient estudianteClient;

    @Mock
    private EvaluacionClient evaluacionClient;

    @InjectMocks
    private ReporteService reporteService;

    @Test
    void debeRetornarReporteConDatosCorrectos_CuandoAmbosServiciosResponden() {
        // 1. Preparar el escenario (Mocks)
        List<Object> mockEstudiantes = List.of(new Object(), new Object()); // Simulamos 2 estudiantes

        // Simulamos un 6.5 y un 5.9 (Su promedio exacto es 6.2)
        List<Map<String, Object>> mockNotas = List.of(
                Map.of("nota", 6.5),
                Map.of("nota", 5.9)
        );

        when(estudianteClient.listarTodos()).thenReturn(mockEstudiantes);
        when(evaluacionClient.listarTodas()).thenReturn(mockNotas);

        // 2. Ejecutar la acción
        ReporteGeneralDto resultado = reporteService.obtenerReporteGeneral();

        // 3. Verificar resultados (Asserts)
        assertEquals(2, resultado.getTotalEstudiantes());
        assertEquals(6.2, resultado.getPromedioGeneralColegio());
        assertEquals("Reporte BFF sincronizado con Estudiantes y Notas 🚀", resultado.getEstado());
    }

    @Test
    void debeRetornarPromedioCero_CuandoEvaluacionServiceFalla() {
        // 1. Preparar el escenario
        List<Object> mockEstudiantes = List.of(new Object()); // 1 estudiante

        when(estudianteClient.listarTodos()).thenReturn(mockEstudiantes);
        // Simulamos que el microservicio de evaluaciones colapsó
        when(evaluacionClient.listarTodas()).thenThrow(new RuntimeException("Simulando error 503"));

        // 2. Ejecutar la acción
        ReporteGeneralDto resultado = reporteService.obtenerReporteGeneral();

        // 3. Verificar resultados
        assertEquals(1, resultado.getTotalEstudiantes());
        assertEquals(0.0, resultado.getPromedioGeneralColegio()); // Debe ser 0.0 por el catch
        assertEquals("Aviso: Evaluacion-Service no responde. Promedio en 0.", resultado.getEstado());
    }

    @Test
    void debeRetornarTotalCero_CuandoEstudianteServiceFalla() {
        // 1. Preparar el escenario
        // Simulamos que el microservicio de estudiantes colapsó
        when(estudianteClient.listarTodos()).thenThrow(new RuntimeException("Simulando error de red"));

        // 2. Ejecutar la acción
        ReporteGeneralDto resultado = reporteService.obtenerReporteGeneral();

        // 3. Verificar resultados
        assertEquals(0, resultado.getTotalEstudiantes()); // Debe ser 0 por el catch
        assertEquals("Aviso: Estudiante-Service no responde.", resultado.getEstado());
    }
}