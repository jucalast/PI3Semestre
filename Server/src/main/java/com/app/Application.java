package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Classe principal da aplicação Spring Boot.
 *
 * Esta é a classe de inicialização da aplicação que configura e inicia o
 * contexto Spring Boot.
 * Utiliza a anotação {@link SpringBootApplication} para habilitar a
 * configuração automática e
 * a detecção de componentes.
 */
@SpringBootApplication
@EntityScan(basePackages = "com.app.model")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
