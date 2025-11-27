package com.nutrix.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow ALL URLs
                .allowedOrigins("http://localhost:5173","https://nutrix-seven.vercel.app", "*") // Allow React Frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow actions
                .allowedHeaders("*")
                .allowCredentials(false); // Set to true if you need cookies, but false is easier for hackathons
    }
}