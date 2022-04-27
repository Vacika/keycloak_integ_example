package com.vaskoj.keycloakauth.config

import com.vaskoj.keycloakauth.config.handler.KeycloakAuthenticationFailureHandler
import com.vaskoj.keycloakauth.config.handler.RestAccessDeniedHandler
import org.keycloak.adapters.springsecurity.KeycloakConfiguration
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy

// Defines all annotations that are needed to integrate Keycloak in Spring Security
@KeycloakConfiguration
internal class SecurityConfig(
    private val accessDeniedHandler: RestAccessDeniedHandler,
    private val authFailureHandler: KeycloakAuthenticationFailureHandler
): KeycloakWebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        super.configure(http)
        http.csrf().disable().cors().disable()
            .authorizeRequests()
            .antMatchers("/api/v1/public/**").permitAll()
            .antMatchers("/api/v1/regular/**").hasRole(REGULAR_ROLE)
            .antMatchers("/api/v1/admin/**").hasRole(ADMIN_ROLE)
            .anyRequest()
            .authenticated()

        //Custom error handler
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler)
    }

    // Disable default role prefix ROLE_
    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        val keycloakAuthenticationProvider = keycloakAuthenticationProvider()
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(SimpleAuthorityMapper())
        auth.authenticationProvider(keycloakAuthenticationProvider)
    }

    // Register authentication strategy for public or confidential applications
    @Bean
    override fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy {
        return RegisterSessionAuthenticationStrategy(SessionRegistryImpl())
    }

    //Keycloak auth exception handler
    @Bean
    @Throws(Exception::class)
    override fun keycloakAuthenticationProcessingFilter(): KeycloakAuthenticationProcessingFilter {
        val filter = KeycloakAuthenticationProcessingFilter(authenticationManagerBean())
        filter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy())
        filter.setAuthenticationFailureHandler(authFailureHandler)
        return filter
    }

    companion object {
        private const val REGULAR_ROLE = "REGULAR"
        private const val ADMIN_ROLE = "ADMIN"
    }
}