package com.vaskoj.keycloakauth.config.handler

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException

@RestControllerAdvice
class GlobalControllerAdvice {
    @ExceptionHandler(HttpClientErrorException::class)
    fun handleUnauthorized(e: HttpClientErrorException): ResponseEntity<String> {
        return ResponseEntity.status(e.statusCode).body(e.responseBodyAsString)
    }
}