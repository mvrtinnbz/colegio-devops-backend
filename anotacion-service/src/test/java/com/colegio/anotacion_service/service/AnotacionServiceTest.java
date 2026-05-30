package com.colegio.anotacion_service.service;

import com.colegio.anotacion_service.entity.Anotacion;
import com.colegio.anotacion_service.repository.AnotacionRepository;
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
public class AnotacionServiceTest {

    @Mock
    private AnotacionRepository anotacionRepository;

    @InjectMocks
    private AnotacionService anotacionService;

    private Anotacion anotacionMock;

    @BeforeEach
    void setUp() {
        anotacionMock = new Anotacion();
    }

    @Test
    void testListarTodas() {
        when(anotacionRepository.findAll()).thenReturn(List.of(anotacionMock));

        List<Anotacion> resultado = anotacionService.listarTodas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(anotacionRepository, times(1)).findAll();
    }

    @Test
    void testListarPorEstudiante() {
        when(anotacionRepository.findByEstudianteId(1L)).thenReturn(List.of(anotacionMock));

        List<Anotacion> resultado = anotacionService.listarPorEstudiante(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(anotacionRepository, times(1)).findByEstudianteId(1L);
    }

    @Test
    void testGuardar() {
        when(anotacionRepository.save(any(Anotacion.class))).thenReturn(anotacionMock);

        Anotacion resultado = anotacionService.guardar(anotacionMock);

        assertNotNull(resultado);
        verify(anotacionRepository, times(1)).save(any(Anotacion.class));
    }
}