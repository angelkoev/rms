package com.rms.config;


import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableCaching
@Configuration
//@EnableAspectJAutoProxy
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }
}