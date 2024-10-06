package com.app;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Classe principal da aplicação Spring Boot.
 *
 * Esta é a classe de inicialização da aplicação que configura e inicia o
 * contexto Spring Boot. Utiliza a anotação {@link SpringBootApplication} para habilitar a
 * configuração automática e a detecção de componentes.
 *
 * Além disso, a classe carrega variáveis de ambiente usando a biblioteca Dotenv
 * e as define como propriedades do sistema, permitindo que a aplicação acesse
 * configurações sensíveis, como credenciais de banco de dados e configurações do cliente Google.
 *
 * @author Giovanni
 * @version 1.0
 * @since 2024-10-05
 */
@SpringBootApplication
@EntityScan(basePackages = "com.app.model")
@EnableJpaRepositories(basePackages = "com.app.repository")
@EnableWebSecurity
@Slf4j
public class Application {

    /**
     * Método principal que inicia a aplicação Spring Boot.
     *
     * Este método carrega as variáveis de ambiente a partir de um arquivo .env
     * e as define como propriedades do sistema. Ele também registra as informações
     * de configuração carregadas e inicia o contexto da aplicação.
     *
     * @param args Argumentos de linha de comando passados durante a inicialização.
     */
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID"));
        System.setProperty("GOOGLE_CLIENT_SECRET", dotenv.get("GOOGLE_CLIENT_SECRET"));

        log.info("Iniciando a aplicação com os seguintes parâmetros:");
        log.info("DB_URL: {}", System.getProperty("DB_URL"));
        log.info("DB_USERNAME: {}", System.getProperty("DB_USERNAME"));
        log.info("GOOGLE_CLIENT_ID: {}", System.getProperty("GOOGLE_CLIENT_ID"));

        SpringApplication.run(Application.class, args);
    }
}
