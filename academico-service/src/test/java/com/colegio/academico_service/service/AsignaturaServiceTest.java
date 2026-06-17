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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        asignaturaMock.setNombre("Matematica");
    }


    @Test
    void testListarTodas() {

        when(asignaturaRepository.findAll())
                .thenReturn(List.of(asignaturaMock));


        List<Asignatura> resultado =
                asignaturaService.listarTodas();


        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        verify(asignaturaRepository).findAll();
    }


    @Test
    void testGuardar() {

        when(asignaturaRepository.save(any()))
                .thenReturn(asignaturaMock);


        Asignatura resultado =
                asignaturaService.guardar(asignaturaMock);


        assertNotNull(resultado);

        verify(asignaturaRepository)
                .save(any());
    }


    @Test
    void testListarPorCurso() {

        when(asignaturaRepository.findByCursoId(1L))
                .thenReturn(List.of(asignaturaMock));


        List<Asignatura> resultado =
                asignaturaService.listarPorCurso(1L);


        assertEquals(1, resultado.size());
    }


    @Test
    void testActualizarExiste() {


        when(asignaturaRepository.findById(1L))
                .thenReturn(Optional.of(asignaturaMock));


        when(asignaturaRepository.save(any()))
                .thenReturn(asignaturaMock);


        Asignatura resultado =
                asignaturaService.actualizar(
                        1L,
                        asignaturaMock
                );


        assertNotNull(resultado);


        verify(asignaturaRepository)
                .save(any());
    }



    @Test
    void testActualizarNoExiste() {


        when(asignaturaRepository.findById(1L))
                .thenReturn(Optional.empty());


        Asignatura resultado =
                asignaturaService.actualizar(
                        1L,
                        asignaturaMock
                );


        assertNull(resultado);
    }



    @Test
    void testEliminarExiste() {


        when(asignaturaRepository.existsById(1L))
                .thenReturn(true);


        boolean resultado =
                asignaturaService.eliminar(1L);


        assertTrue(resultado);


        verify(asignaturaRepository)
                .deleteById(1L);
    }



    @Test
    void testEliminarNoExiste() {


        when(asignaturaRepository.existsById(1L))
                .thenReturn(false);


        boolean resultado =
                asignaturaService.eliminar(1L);


        assertFalse(resultado);


        verify(asignaturaRepository, never())
                .deleteById(1L);
    }

}