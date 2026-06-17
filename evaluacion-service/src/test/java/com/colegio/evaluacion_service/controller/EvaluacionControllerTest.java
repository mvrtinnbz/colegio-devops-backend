package com.colegio.evaluacion_service.controller;


import com.colegio.evaluacion_service.entity.Evaluacion;
import com.colegio.evaluacion_service.service.EvaluacionService;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class EvaluacionControllerTest {



    @Mock
    private EvaluacionService service;


    @InjectMocks
    private EvaluacionController controller;



    @Test
    void listarTodas(){


        when(service.listarTodas())
                .thenReturn(List.of(new Evaluacion()));


        ResponseEntity<List<Evaluacion>> response =
                controller.listarTodas();



        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

    }





    @Test
    void listarPorEstudiante(){


        when(service.listarPorEstudiante(1L))
                .thenReturn(List.of(new Evaluacion()));


        ResponseEntity<List<Evaluacion>> response =
                controller.listarPorEstudiante(1L);



        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

    }





    @Test
    void guardar(){


        Evaluacion e = new Evaluacion();


        when(service.guardar(e))
                .thenReturn(e);



        ResponseEntity<Evaluacion> response =
                controller.guardar(e);



        assertEquals(
                HttpStatus.CREATED,
                response.getStatusCode()
        );

    }





    @Test
    void promedio(){


        when(service.calcularPromedioAsignatura(
                1L,
                "Matematicas"
        ))
        .thenReturn(6.0);



        ResponseEntity<Double> response =
                controller.obtenerPromedio(
                        1L,
                        "Matematicas"
                );


        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );


    }

}