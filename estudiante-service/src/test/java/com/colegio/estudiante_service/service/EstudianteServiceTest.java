package com.colegio.estudiante_service.service;

import com.colegio.estudiante_service.entity.Estudiante;
import com.colegio.estudiante_service.repository.EstudianteRepository;

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
public class EstudianteServiceTest {


    @Mock
    private EstudianteRepository estudianteRepository;


    @InjectMocks
    private EstudianteService service;


    private Estudiante estudiante;



    @BeforeEach
    void setUp(){

        estudiante = new Estudiante();

        estudiante.setNombres("Juan");
        estudiante.setApellidos("Perez");

    }



    @Test
    void registrarEstudiante(){

        when(estudianteRepository.save(any()))
                .thenReturn(estudiante);


        Estudiante resultado =
                service.registrarEstudiante(estudiante);


        assertNotNull(resultado);

        verify(estudianteRepository)
                .save(any());
    }




    @Test
    void obtenerTodos(){

        when(estudianteRepository.findAll())
                .thenReturn(List.of(estudiante));


        List<Estudiante> resultado =
                service.obtenerTodos();


        assertEquals(1, resultado.size());

    }




    @Test
    void obtenerPorIdExiste(){

        when(estudianteRepository.findById(1L))
                .thenReturn(Optional.of(estudiante));


        Optional<Estudiante> resultado =
                service.obtenerPorId(1L);


        assertTrue(resultado.isPresent());

    }




    @Test
    void obtenerPorCurso(){

        when(estudianteRepository.findByCursoId(1L))
                .thenReturn(List.of(estudiante));


        List<Estudiante> resultado =
                service.obtenerPorCurso(1L);


        assertFalse(resultado.isEmpty());

    }





    @Test
    void actualizarExiste(){


        when(estudianteRepository.findById(1L))
                .thenReturn(Optional.of(estudiante));


        when(estudianteRepository.save(any()))
                .thenReturn(estudiante);



        Estudiante resultado =
                service.actualizarEstudiante(
                        1L,
                        estudiante
                );


        assertNotNull(resultado);


        verify(estudianteRepository)
                .save(any());

    }





    @Test
    void actualizarNoExiste(){


        when(estudianteRepository.findById(1L))
                .thenReturn(Optional.empty());


        assertThrows(
                RuntimeException.class,
                () -> service.actualizarEstudiante(
                        1L,
                        estudiante
                )
        );

    }





    @Test
    void eliminarEstudiante(){

        service.eliminarEstudiante(1L);


        verify(estudianteRepository)
                .deleteById(1L);

    }

}