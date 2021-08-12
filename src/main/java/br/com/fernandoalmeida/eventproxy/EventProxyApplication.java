package br.com.fernandoalmeida.eventproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Eventproxy API", description = "EventProxy api allows any external service to be called, with an optional delay", contact = @Contact(name = "Fernando Costa de Almeida", email = "fernando.c.almeida@gmail.com")))
public class EventProxyApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(EventProxyApplication.class, args);
    }
}
