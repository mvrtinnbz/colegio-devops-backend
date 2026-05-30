package com.colegio.comunicacion_service.controller;

import com.colegio.comunicacion_service.entity.Aviso;
import com.colegio.comunicacion_service.service.AvisoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AvisoControllerTest {
    @Mock private AvisoService avisoService;
    @InjectMocks private AvisoController avisoController;

    @Test
    void testListarAvisos() {
        when(avisoService.listarTodos()).thenReturn(List.of(new Aviso()));
        assertEquals(HttpStatus.OK, avisoController.listarAvisos().getStatusCode());
    }

    @Test
    void testCrearAviso() {
        Aviso aviso = new Aviso();
        when(avisoService.guardar(any())).thenReturn(aviso);
        assertEquals(HttpStatus.CREATED, avisoController.crearAviso(aviso).getStatusCode());
    }
}