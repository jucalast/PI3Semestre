package com.app;

import com.app.config.RSAKeyRecord;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Classe principal da aplicação Spring Boot.
 *
 * Esta é a classe de inicialização da aplicação que configura e inicia o
 * contexto Spring Boot.
 * Utiliza a anotação {@link SpringBootApplication} para habilitar a
 * configuração automática e
 * a detecção de componentes.
 */
@EnableConfigurationProperties(RSAKeyRecord.class)
@SpringBootApplication
@EntityScan(basePackages = "com.app.model")
@EnableJpaRepositories(basePackages = "com.app.repository")
public class Application {
    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID"));
        System.setProperty("GOOGLE_CLIENT_SECRET", dotenv.get("GOOGLE_CLIENT_SECRET"));

        SpringApplication.run(Application.class, args);
    }
}
