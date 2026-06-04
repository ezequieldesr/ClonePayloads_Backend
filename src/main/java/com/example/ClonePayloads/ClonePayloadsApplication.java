package com.example.ClonePayloads;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ClonePayloadsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClonePayloadsApplication.class, args);
	}
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("https://payload-cloner.vercel.app/")
                        .allowedMethods("POST")
                        .allowedHeaders("*");
            }
        };
    }
}
