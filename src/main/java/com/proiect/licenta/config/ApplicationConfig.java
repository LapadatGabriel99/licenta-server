package com.proiect.licenta.config;

import com.proiect.licenta.controller.AnswerController;
import com.proiect.licenta.converter.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }

    @Bean
    public UserConverter userConverter() {

        return new UserConverter();
    }

    @Bean
    public CategoryConverter categoryConverter() {

        return new CategoryConverter();
    }

    @Bean
    public TestConverter testConverter() {

        return new TestConverter();
    }

    @Bean
    public QuestionConverter questionConverter() {

        return new QuestionConverter();
    }

    @Bean
    public AnswerConverter answerConverter() {

        return new AnswerConverter();
    }

    @Bean
    public LoginConverter loginConverter() {

        return new LoginConverter();
    }
}
