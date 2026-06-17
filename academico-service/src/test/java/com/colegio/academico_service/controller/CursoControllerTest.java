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


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class CursoControllerTest {


    @Mock
    private CursoService cursoService;


    @InjectMocks
    private CursoController controller;


    private Curso curso;



    @BeforeEach
    void setUp(){

        curso = new Curso();

        curso.setGrado("1");
        curso.setLetra("A");
        curso.setNivel("Basica");

    }



    @Test
    void listarCursos(){


        when(cursoService.listarTodos())
                .thenReturn(List.of(curso));


        ResponseEntity<List<Curso>> response =
                controller.listarCursos();


        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

    }




    @Test
    void obtenerCursoExiste(){


        when(cursoService.obtenerPorId(1L))
                .thenReturn(curso);


        ResponseEntity<Curso> response =
                controller.obtenerCurso(1L);



        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

    }




    @Test
    void obtenerCursoNoExiste(){


        when(cursoService.obtenerPorId(1L))
                .thenReturn(null);


        ResponseEntity<Curso> response =
                controller.obtenerCurso(1L);



        assertEquals(
                HttpStatus.NOT_FOUND,
                response.getStatusCode()
        );

    }




    @Test
    void crearCursoCorrecto(){


        when(cursoService.listarTodos())
                .thenReturn(List.of());


        when(cursoService.guardar(curso))
                .thenReturn(curso);



        ResponseEntity<?> response =
                controller.crearCurso(curso);



        assertEquals(
                HttpStatus.CREATED,
                response.getStatusCode()
        );

    }




    @Test
    void crearCursoDuplicado(){


        when(cursoService.listarTodos())
                .thenReturn(List.of(curso));



        ResponseEntity<?> response =
                controller.crearCurso(curso);



        assertEquals(
                HttpStatus.CONFLICT,
                response.getStatusCode()
        );

    }

}