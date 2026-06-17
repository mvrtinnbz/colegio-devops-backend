package com.colegio.evaluacion_service.service;


import com.colegio.evaluacion_service.entity.Evaluacion;
import com.colegio.evaluacion_service.repository.EvaluacionRepository;


import org.junit.jupiter.api.BeforeEach;
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


    @Mock
    private EvaluacionRepository repository;


    @InjectMocks
    private EvaluacionService service;


    private Evaluacion evaluacion;



    @BeforeEach
    void setUp(){

        evaluacion = new Evaluacion();

        evaluacion.setAsignatura("Matematicas");
        evaluacion.setNota(6.0);

    }




    @Test
    void listarTodas(){


        when(repository.findAll())
                .thenReturn(List.of(evaluacion));


        List<Evaluacion> resultado =
                service.listarTodas();


        assertEquals(
                1,
                resultado.size()
        );

    }





    @Test
    void listarPorEstudiante(){


        when(repository.findByEstudianteId(1L))
                .thenReturn(List.of(evaluacion));


        List<Evaluacion> resultado =
                service.listarPorEstudiante(1L);


        assertFalse(resultado.isEmpty());

    }





    @Test
    void guardar(){


        when(repository.save(any()))
                .thenReturn(evaluacion);


        Evaluacion resultado =
                service.guardar(evaluacion);



        assertNotNull(resultado);


        verify(repository)
                .save(any());

    }





    @Test
    void promedioSinNotas(){


        when(repository.findByEstudianteId(1L))
                .thenReturn(List.of());


        Double resultado =
                service.calcularPromedioAsignatura(
                        1L,
                        "Matematicas"
                );


        assertEquals(
                0.0,
                resultado
        );

    }





    @Test
    void promedioConNotas(){


        Evaluacion otra = new Evaluacion();

        otra.setAsignatura("Matematicas");
        otra.setNota(5.0);



        when(repository.findByEstudianteId(1L))
                .thenReturn(
                        List.of(
                                evaluacion,
                                otra
                        )
                );


        Double resultado =
                service.calcularPromedioAsignatura(
                        1L,
                        "Matematicas"
                );



        assertEquals(
                5.5,
                resultado
        );

    }

}