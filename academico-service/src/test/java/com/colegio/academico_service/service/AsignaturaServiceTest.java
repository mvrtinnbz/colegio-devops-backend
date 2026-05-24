package com.colegio.academico_service.service;

import com.colegio.academico_service.entity.Asignatura;
import com.colegio.academico_service.repository.AsignaturaRepository;
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
public class AsignaturaServiceTest {

    @Mock
    private AsignaturaRepository asignaturaRepository;

    @InjectMocks
    private AsignaturaService asignaturaService;

    private Asignatura asignaturaMock;

    @BeforeEach
    void setUp() {
        asignaturaMock = new Asignatura();
    }

    @Test
    void testListarTodas() {
        when(asignaturaRepository.findAll()).thenReturn(List.of(asignaturaMock));

        List<Asignatura> resultado = asignaturaService.listarTodas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(asignaturaRepository, times(1)).findAll();
    }

    @Test
    void testGuardar() {
        when(asignaturaRepository.save(any(Asignatura.class))).thenReturn(asignaturaMock);

        Asignatura resultado = asignaturaService.guardar(asignaturaMock);

        assertNotNull(resultado);
        verify(asignaturaRepository, times(1)).save(any(Asignatura.class));
    }

    @Test
    void testListarPorCurso() {
        when(asignaturaRepository.findByCursoId(1L)).thenReturn(List.of(asignaturaMock));

        List<Asignatura> resultado = asignaturaService.listarPorCurso(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(asignaturaRepository, times(1)).findByCursoId(1L);
    }
}