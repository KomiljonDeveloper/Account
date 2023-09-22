package com.example.card_user;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@OpenAPIDefinition(
        tags = {@Tag(name = "get"),@Tag(name = "post"),@Tag(name = "put"),@Tag(name = "delete")},

        info = @Info(
                version = "v: 3.0.2",
                title = "This is \"Simple marketplace\" project",
                description = "This project is writing Java programming language",
                contact = @Contact(
                        name = "Komiljon Bakhromov",
                        url = "http://t.me/Bizbiroddiy_Inson",
                        email = "bakhromovkomiljon@gmail.com"

                ),license = @License(name = "For License",
                                      url = "http://t.me/Bizbiroddiy_Inson"
        )
        ),servers = {@Server(url = "http://localhost:8081")}
)
@SpringBootApplication
public class CardUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardUserApplication.class, args);
    }

}
