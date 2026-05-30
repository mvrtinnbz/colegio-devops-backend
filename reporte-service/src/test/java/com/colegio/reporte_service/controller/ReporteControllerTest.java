package com.colegio.reporte_service.controller;

import com.colegio.reporte_service.dto.ReporteGeneralDto;
import com.colegio.reporte_service.service.ReporteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReporteControllerTest {
    @Mock private ReporteService reporteService;
    @InjectMocks private ReporteController reporteController;

    @Test
    void testObtenerReporteGeneral() {
        ReporteGeneralDto dto = new ReporteGeneralDto(10, 5.5, "90%", "OK");
        when(reporteService.obtenerReporteGeneral()).thenReturn(dto);
        
        ResponseEntity<ReporteGeneralDto> response = reporteController.obtenerReporteGeneral();
        
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }
}