package com.colegio.asistencia_service.service;

import com.colegio.asistencia_service.entity.Asistencia;
import com.colegio.asistencia_service.repository.AsistenciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AsistenciaServiceTest {

    @Mock
    private AsistenciaRepository asistenciaRepository;

    @InjectMocks
    private AsistenciaService asistenciaService;

    private Asistencia asistenciaMock;

    @BeforeEach
    void setUp() {
        asistenciaMock = new Asistencia();
    }

    @Test
    void testListarTodas() {
        when(asistenciaRepository.findAll()).thenReturn(List.of(asistenciaMock));

        List<Asistencia> resultado = asistenciaService.listarTodas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(asistenciaRepository, times(1)).findAll();
    }

    @Test
    void testGuardar() {
        when(asistenciaRepository.save(any(Asistencia.class))).thenReturn(asistenciaMock);

        Asistencia resultado = asistenciaService.guardar(asistenciaMock);

        assertNotNull(resultado);
        verify(asistenciaRepository, times(1)).save(any(Asistencia.class));
    }
}