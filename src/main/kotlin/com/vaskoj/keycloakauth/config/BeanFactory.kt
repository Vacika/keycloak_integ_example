package com.vaskoj.keycloakauth.config

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class BeanFactory {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate =
        builder.build()

    // Use Spring Boot property files instead of default keycloak.json
    @Bean
    fun KeycloakConfigResolver(): KeycloakSpringBootConfigResolver? {
        return KeycloakSpringBootConfigResolver()
    }
}