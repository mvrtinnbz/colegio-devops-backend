package com.colegio.estudiante_service.service;

import com.colegio.estudiante_service.entity.Estudiante;
import com.colegio.estudiante_service.repository.EstudianteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstudianteServiceTest {
    @Mock private EstudianteRepository repository;
    @InjectMocks private EstudianteService service;

    @Test
    void testRegistrar() {
        Estudiante e = new Estudiante();
        when(repository.save(any())).thenReturn(e);
        assertNotNull(service.registrarEstudiante(e));
    }

    @Test
    void testActualizar() {
        Estudiante e = new Estudiante();
        when(repository.findById(1L)).thenReturn(Optional.of(e));
        when(repository.save(any())).thenReturn(e);
        assertNotNull(service.actualizarEstudiante(1L, e));
    }

    @Test
    void testEliminar() {
        doNothing().when(repository).deleteById(1L);
        service.eliminarEstudiante(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}