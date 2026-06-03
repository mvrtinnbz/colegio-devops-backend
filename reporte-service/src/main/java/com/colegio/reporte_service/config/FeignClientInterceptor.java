package com.colegio.reporte_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // Obtenemos la petición web actual
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            // Extraemos el Token de la cabecera
            String token = attributes.getRequest().getHeader("Authorization");
            if (token != null) {
                // Se lo pegamos a la nueva petición interna de Feign
                requestTemplate.header("Authorization", token);
            }
        }
    }
}