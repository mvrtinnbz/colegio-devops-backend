package com.colegio.evaluacion_service.service;

import com.colegio.evaluacion_service.entity.Evaluacion;
import com.colegio.evaluacion_service.repository.EvaluacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EvaluacionServiceTest {
    @Mock private EvaluacionRepository repository;
    @InjectMocks private EvaluacionService service;

    @Test
    void testCalcularPromedio() {
        Evaluacion e1 = new Evaluacion(); e1.setAsignatura("Matematicas"); e1.setNota(5.0);
        Evaluacion e2 = new Evaluacion(); e2.setAsignatura("Matematicas"); e2.setNota(6.0);
        when(repository.findByEstudianteId(1L)).thenReturn(List.of(e1, e2));
        
        Double promedio = service.calcularPromedioAsignatura(1L, "Matematicas");
        assertEquals(5.5, promedio);
    }
}