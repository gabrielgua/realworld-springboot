package com.gabrielgua.realworld.infra.config;


import com.gabrielgua.realworld.api.model.profile.ProfileResponse;
import com.gabrielgua.realworld.domain.model.Profile;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        return modelMapper;
    }


}

