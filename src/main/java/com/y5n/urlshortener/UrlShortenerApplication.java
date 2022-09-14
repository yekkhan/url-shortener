package com.y5n.urlshortener;

import com.y5n.urlshortener.entity.Role;
import com.y5n.urlshortener.entity.User;
import com.y5n.urlshortener.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class UrlShortenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_PREMIUM"));

            userService.saveUser(new User(null, "Yek Khan", "yk", "1234", new ArrayList<>()));
            userService.saveUser(new User(null, "Jia Le", "jl", "1234", new ArrayList<>()));

            userService.addRoleToUser("yk", "ROLE_USER");
            userService.addRoleToUser("yk", "ROLE_PREMIUM");
            userService.addRoleToUser("jl", "ROLE_USER");
            userService.addRoleToUser("jl", "ROLE_PREMIUM");
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
