package com.github.sadufcg;

import com.github.sadufcg.pojo.Token;
import com.github.sadufcg.repositories.TokenRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init (TokenRepository tokenRepository){
        Token token_default = new Token("tokenDefault");
        return (event) -> tokenRepository.save(token_default);
    }

}