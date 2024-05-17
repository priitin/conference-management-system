package org.conference.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI config() {
        var contact = new Contact();
        contact.setName("Priit Aarne");
        contact.setEmail("priitaarne@gmail.com");

        var info = new Info()
                .title("Conference Management System")
                .version("1.0")
                .contact(contact);

        var localhost = new Server();
        localhost.setUrl("http://localhost:8080");
        localhost.setDescription("Localhost");

        return new OpenAPI().info(info).servers(List.of(localhost));
    }
}
