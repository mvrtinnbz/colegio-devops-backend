package com.colegio.estudiante_service.controller;


import com.colegio.estudiante_service.entity.Estudiante;
import com.colegio.estudiante_service.service.EstudianteService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class EstudianteControllerTest {



    @Mock
    private EstudianteService service;



    @InjectMocks
    private EstudianteController controller;



    private Estudiante estudiante;




    @BeforeEach
    void setUp(){

        estudiante = new Estudiante();

    }





    @Test
    void listar(){

        when(service.obtenerTodos())
                .thenReturn(List.of(estudiante));


        ResponseEntity<List<Estudiante>> response =
                controller.listarEstudiantes();


        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

    }






    @Test
    void obtenerExiste(){

        when(service.obtenerPorId(1L))
                .thenReturn(Optional.of(estudiante));


        ResponseEntity<Estudiante> response =
                controller.obtenerEstudiante(1L);


        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

    }





    @Test
    void obtenerNoExiste(){


        when(service.obtenerPorId(1L))
                .thenReturn(Optional.empty());


        ResponseEntity<Estudiante> response =
                controller.obtenerEstudiante(1L);


        assertEquals(
                HttpStatus.NOT_FOUND,
                response.getStatusCode()
        );

    }





    @Test
    void crear(){

        when(service.registrarEstudiante(estudiante))
                .thenReturn(estudiante);



        ResponseEntity<Estudiante> response =
                controller.matricularEstudiante(estudiante);



        assertEquals(
                HttpStatus.CREATED,
                response.getStatusCode()
        );

    }





    @Test
    void actualizarOK(){


        when(service.actualizarEstudiante(1L, estudiante))
                .thenReturn(estudiante);



        ResponseEntity<Estudiante> response =
                controller.actualizarEstudiante(
                        1L,
                        estudiante
                );


        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

    }





    @Test
    void actualizarError(){


        when(service.actualizarEstudiante(1L, estudiante))
                .thenThrow(new RuntimeException());


        ResponseEntity<Estudiante> response =
                controller.actualizarEstudiante(
                        1L,
                        estudiante
                );


        assertEquals(
                HttpStatus.NOT_FOUND,
                response.getStatusCode()
        );

    }





    @Test
    void eliminar(){


        ResponseEntity<Void> response =
                controller.eliminarEstudiante(1L);


        assertEquals(
                HttpStatus.NO_CONTENT,
                response.getStatusCode()
        );


        verify(service)
                .eliminarEstudiante(1L);

    }




    @Test
void listarPorCurso(){

    when(service.obtenerPorCurso(1L))
            .thenReturn(List.of(estudiante));


    ResponseEntity<List<Estudiante>> response =
            controller.listarPorCurso(1L);


    assertEquals(
            HttpStatus.OK,
            response.getStatusCode()
    );


    assertNotNull(response.getBody());


    verify(service)
            .obtenerPorCurso(1L);
}

}