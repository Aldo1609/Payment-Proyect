package com.aldob.payment.consumer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort + contextPath)
                                .description("Servidor de desarrollo"),
                        new Server()
                                .url("https://api.company.com" + contextPath)
                                .description("Servidor de producción")
                ))
                .info(new Info()
                        .title("Payment Consumer Service API")
                        .version("1.0.0")
                        .description("""
                            API REST para procesamiento de pagos con pasarela de pago.
                            
                            Características:
                            - Reception de peticiones de pago vía Kafka
                            - Validacion y procesamiento de datos de compra
                            - Integracion con Stripe como pasarela de pago.
                            - Actualización de orden de pago.
                            """)
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("aldobecerra1609@gmail.com")
                                .url("https://www.linkedin.com/in/aldo-isaias-becerra-campos-591621200/")));

    }
}