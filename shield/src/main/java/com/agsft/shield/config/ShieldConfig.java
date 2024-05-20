package com.agsft.shield.config;

import com.agsft.shield.security.CustomAuditAware;
import com.agsft.shield.service.UserService;
import com.agsft.shield.service.UserServiceImpl;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.modelmapper.ModelMapper;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ShieldConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public OperationCustomizer customize() {
        return (operation, handlerMethod) -> operation.addParametersItem(
                new Parameter()
                        .in("header")
                        .required(false)
                        .description("Authorization")
                        .name("security"));
    }

    @Bean
    public UserService userService(){
        return new UserServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new CustomAuditAware();
    }

}
