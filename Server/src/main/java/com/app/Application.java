package com.app;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot.
 *
 * Esta é a classe de inicialização da aplicação que configura e inicia o contexto Spring Boot.
 * Utiliza a anotação {@link SpringBootApplication} para habilitar a configuração automática e
 * a detecção de componentes.
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

        SpringApplication.run(Application.class, args);
    }
}
