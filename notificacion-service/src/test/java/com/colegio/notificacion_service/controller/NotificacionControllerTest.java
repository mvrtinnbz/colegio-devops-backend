package com.colegio.notificacion_service.controller;

import com.colegio.notificacion_service.dto.MensajeDto;
import com.colegio.notificacion_service.service.NotificacionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificacionControllerTest {
    @Mock private NotificacionService service;
    @InjectMocks private NotificacionController controller;

    @Test
    void testEnviarNotificacion() {
        MensajeDto dto = new MensajeDto();
        dto.setDestinatario("test@test.com");
        dto.setAsunto("Asunto");
        dto.setContenido("Contenido");
        
        when(service.enviarCorreo(any())).thenReturn("Notificación enviada con éxito a: test@test.com");
        
        ResponseEntity<String> response = controller.enviarNotificacion(dto);
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().contains("test@test.com"));
    }
}