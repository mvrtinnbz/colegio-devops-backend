package com.colegio.auth_service.client;

import com.colegio.auth_service.dto.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "usuario-service", url = "http://usuario-service:8092")
public interface UsuarioFeignClient {

    @GetMapping("/api/usuarios/buscar-por-email")
    UsuarioDto buscarPorEmail(@RequestParam("email") String email);
}