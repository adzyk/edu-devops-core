package ru.oa2.edu.api.application.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authorization.AuthorizationEventPublisher;
import org.springframework.security.authorization.SpringAuthorizationEventPublisher;

public class ApplicationConfig {

    @Bean
    public AuthorizationEventPublisher authorizationEventPublisher
            (ApplicationEventPublisher applicationEventPublisher) {
        return new SpringAuthorizationEventPublisher(applicationEventPublisher);
    }
}
