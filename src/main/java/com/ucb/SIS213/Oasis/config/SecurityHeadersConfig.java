package com.ucb.SIS213.Oasis.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Configuración de headers de seguridad HTTP
 * Corrige las vulnerabilidades detectadas por OWASP ZAP
 */
@Configuration
public class SecurityHeadersConfig {

    @Component
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public static class SecurityHeadersFilter implements Filter {

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            // Content Security Policy (CSP)
            // Permite recursos de los mismos orígenes y CDNs necesarios
            String csp = "default-src 'self'; " +
                    "script-src 'self' 'unsafe-inline' 'unsafe-eval' https://code.jquery.com https://cdnjs.cloudflare.com https://maxcdn.bootstrapcdn.com https://stackpath.bootstrapcdn.com https://cdn.auth0.com; " +
                    "style-src 'self' 'unsafe-inline' https://stackpath.bootstrapcdn.com https://cdn.auth0.com https://maxcdn.bootstrapcdn.com; " +
                    "img-src 'self' data: https: blob:; " +
                    "font-src 'self' https://stackpath.bootstrapcdn.com https://cdn.auth0.com; " +
                    "connect-src 'self' https://api.google.dev https://api.ipify.org; " +
                    "frame-src 'self' https://accounts.google.com; " +
                    "object-src 'none'; " +
                    "base-uri 'self'; " +
                    "form-action 'self'; " +
                    "frame-ancestors 'self'; " +
                    "upgrade-insecure-requests;";
            httpResponse.setHeader("Content-Security-Policy", csp);

            // X-Frame-Options: Protección contra clickjacking
            httpResponse.setHeader("X-Frame-Options", "SAMEORIGIN");

            // X-Content-Type-Options: Prevenir MIME type sniffing
            httpResponse.setHeader("X-Content-Type-Options", "nosniff");

            // Strict-Transport-Security (HSTS): Solo para HTTPS
            if (httpRequest.isSecure() || "https".equalsIgnoreCase(httpRequest.getHeader("X-Forwarded-Proto"))) {
                httpResponse.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains; preload");
            }

            // X-XSS-Protection (aunque está deprecado, algunos navegadores aún lo usan)
            httpResponse.setHeader("X-XSS-Protection", "1; mode=block");

            // Referrer-Policy: Controlar qué información del referrer se envía
            httpResponse.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");

            // Permissions-Policy (anteriormente Feature-Policy)
            httpResponse.setHeader("Permissions-Policy", 
                "geolocation=(), microphone=(), camera=(), payment=(), usb=(), magnetometer=(), gyroscope=(), speaker=()");

            // Cache-Control para recursos sensibles
            if (httpRequest.getRequestURI().contains("/api/") || 
                httpRequest.getRequestURI().contains("/login") ||
                httpRequest.getRequestURI().contains("/admin")) {
                httpResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
                httpResponse.setHeader("Pragma", "no-cache");
                httpResponse.setHeader("Expires", "0");
            }

            // Remover headers que revelan información
            httpResponse.setHeader("X-Powered-By", "");
            httpResponse.setHeader("Server", "");

            chain.doFilter(request, response);
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            // No se necesita inicialización
        }

        @Override
        public void destroy() {
            // No se necesita limpieza
        }
    }
}

