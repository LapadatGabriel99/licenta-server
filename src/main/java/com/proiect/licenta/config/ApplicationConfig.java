package com.proiect.licenta.config;

import com.proiect.licenta.converter.UserConverter;
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
}
