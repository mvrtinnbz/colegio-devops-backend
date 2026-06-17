package com.colegio.reporte_service.controller;

import com.colegio.reporte_service.dto.ReporteGeneralDto;
import com.colegio.reporte_service.service.ReporteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ReporteControllerTest {

    @Mock
    private ReporteService reporteService;

    @InjectMocks
    private ReporteController reporteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerReporteGeneral() {
        ReporteGeneralDto mockDto = new ReporteGeneralDto(100, 6.0, "92%", "OK");
        when(reporteService.obtenerReporteGeneral()).thenReturn(mockDto);

        ResponseEntity<ReporteGeneralDto> response = reporteController.obtenerReporteGeneral();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(100, response.getBody().getTotalEstudiantes());
    }
}