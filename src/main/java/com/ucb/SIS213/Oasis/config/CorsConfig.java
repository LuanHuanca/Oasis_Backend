package com.ucb.SIS213.Oasis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {
    
    @Value("${cors.allowed.origins:*}")
    private String allowedOrigins;
    
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Permitir cualquier origen por defecto (para Railway, producción, etc.)
        // Usar addAllowedOriginPattern para permitir cualquier origen incluyendo HTTPS
        config.addAllowedOriginPattern("*");
        
        // Permitir credenciales (cookies, authorization headers, etc.)
        config.setAllowCredentials(true);
        
        // Permitir todos los headers
        config.addAllowedHeader("*");
        
        // Permitir todos los métodos HTTP
        config.addAllowedMethod("*");
        
        // Headers expuestos que el cliente puede leer
        config.addExposedHeader("*");
        
        // Tiempo que el navegador puede cachear la respuesta de preflight
        config.setMaxAge(3600L);
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
