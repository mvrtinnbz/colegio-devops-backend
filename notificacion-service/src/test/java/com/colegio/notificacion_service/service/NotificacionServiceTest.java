package com.colegio.notificacion_service.service;

import com.colegio.notificacion_service.dto.MensajeDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class NotificacionServiceTest {
    @InjectMocks private NotificacionService service;

    @Test
    void testEnviarCorreo() {
        MensajeDto dto = new MensajeDto();
        dto.setDestinatario("test@test.com");
        dto.setAsunto("Asunto");
        dto.setContenido("Contenido");
        
        String resultado = service.enviarCorreo(dto);
        assertTrue(resultado.contains("test@test.com"));
    }
}