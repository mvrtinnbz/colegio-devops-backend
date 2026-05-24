package com.colegio.academico_service.controller;

import com.colegio.academico_service.entity.Curso;
import com.colegio.academico_service.service.CursoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CursoControllerTest {

    @Mock
    private CursoService cursoService;

    @InjectMocks
    private CursoController cursoController;

    private Curso cursoMock;

    @BeforeEach
    void setUp() {
        cursoMock = new Curso();
    }

    @Test
    void testListarCursos() {
        when(cursoService.listarTodos()).thenReturn(List.of(cursoMock));

        ResponseEntity<List<Curso>> response = cursoController.listarCursos();

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(cursoService, times(1)).listarTodos();
    }

    @Test
    void testObtenerCurso_Encontrado() {
        when(cursoService.obtenerPorId(1L)).thenReturn(cursoMock);

        ResponseEntity<Curso> response = cursoController.obtenerCurso(1L);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        verify(cursoService, times(1)).obtenerPorId(1L);
    }

    @Test
    void testObtenerCurso_NoEncontrado() {
        when(cursoService.obtenerPorId(99L)).thenReturn(null);

        ResponseEntity<Curso> response = cursoController.obtenerCurso(99L);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());
        verify(cursoService, times(1)).obtenerPorId(99L);
    }

    @Test
    void testCrearCurso() {
        when(cursoService.guardar(any(Curso.class))).thenReturn(cursoMock);

        ResponseEntity<Curso> response = cursoController.crearCurso(cursoMock);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        verify(cursoService, times(1)).guardar(any(Curso.class));
    }
}