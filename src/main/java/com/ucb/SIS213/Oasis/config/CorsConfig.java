package com.ucb.SIS213.Oasis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {
    
    @Value("${cors.allowed.origins:http://localhost:3000}")
    private String allowedOrigins;
    
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Si se especifica "*", permitir cualquier origen (útil para ngrok, EC2, etc.)
        if ("*".equals(allowedOrigins.trim())) {
            config.addAllowedOriginPattern("*"); // Usar addAllowedOriginPattern para permitir cualquier origen
        } else {
            // Parsear los orígenes permitidos desde la variable de entorno
            // Puede ser una lista separada por comas
            List<String> origins = Arrays.asList(allowedOrigins.split(","));
            origins.forEach(origin -> {
                String trimmedOrigin = origin.trim();
                if (!trimmedOrigin.isEmpty()) {
                    config.addAllowedOrigin(trimmedOrigin);
                }
            });
            config.setAllowCredentials(true); // Permitir credenciales solo si no es "*"
        }
        
        config.addAllowedHeader("*"); // Permitir cualquier encabezado
        config.addAllowedMethod("*"); // Permitir cualquier método (GET, POST, etc.)
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
