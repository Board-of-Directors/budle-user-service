package ru.nsu.fit.directors.userservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(servers = {
    @Server(
        description = "This is the localhost",
        url = "localhost:8080/user"
    ),
    @Server(
        description = "This is the real server",
        url = "80.87.200.185/user"
    )
})
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
