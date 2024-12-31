package dev.ernandorezende.mybooks.config;

import org.hibernate.annotations.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ModelMappingConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
