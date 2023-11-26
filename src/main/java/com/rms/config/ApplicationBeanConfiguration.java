package com.rms.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {



    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

//        modelMapper.addConverter(new Converter<String, LocalDate>() {
//            @Override
//            public LocalDate convert(MappingContext<String, LocalDate> mappingContext) {
//
//                LocalDate parse = LocalDate
//                        .parse(mappingContext.getSource(),
//                                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//
//                return parse;
//            }
//        });

//        Converter<LocalDate, String> localDateToStringConverter = new Converter<LocalDate, String>() {
//            @Override
//            public String convert(MappingContext<LocalDate, String> context) {
//                LocalDate source = context.getSource();
//                return source != null ? source.format(DateTimeFormatter.ISO_LOCAL_DATE) : null;
//            }
//        };

//        modelMapper.addConverter(localDateToStringConverter);

        return modelMapper;
    }
}