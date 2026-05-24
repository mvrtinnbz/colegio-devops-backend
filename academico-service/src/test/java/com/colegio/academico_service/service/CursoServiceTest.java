package com.colegio.academico_service.service;

import com.colegio.academico_service.entity.Curso;
import com.colegio.academico_service.repository.CursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private CursoService cursoService;

    private Curso cursoMock;

    @BeforeEach
    void setUp() {
        cursoMock = new Curso();
    }

    @Test
    void testListarTodos() {
        when(cursoRepository.findAll()).thenReturn(List.of(cursoMock));

        List<Curso> resultado = cursoService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(cursoRepository, times(1)).findAll();
    }

    @Test
    void testGuardar() {
        when(cursoRepository.save(any(Curso.class))).thenReturn(cursoMock);

        Curso resultado = cursoService.guardar(cursoMock);

        assertNotNull(resultado);
        verify(cursoRepository, times(1)).save(any(Curso.class));
    }

    @Test
    void testObtenerPorId_Encontrado() {
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(cursoMock));

        Curso resultado = cursoService.obtenerPorId(1L);

        assertNotNull(resultado);
        verify(cursoRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerPorId_NoEncontrado() {
        when(cursoRepository.findById(99L)).thenReturn(Optional.empty());

        Curso resultado = cursoService.obtenerPorId(99L);

        assertNull(resultado);
        verify(cursoRepository, times(1)).findById(99L);
    }
}