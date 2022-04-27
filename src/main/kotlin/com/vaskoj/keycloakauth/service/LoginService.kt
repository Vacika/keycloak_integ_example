package com.vaskoj.keycloakauth.service

import com.vaskoj.keycloakauth.web.request.LoginRequest
import com.vaskoj.keycloakauth.web.response.LoginResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate


@Service
class LoginService(
    private val restTemplate: RestTemplate,
    @Value("\${app.keycloak.login.url}")
    private val loginUrl: String,
    @Value("\${app.keycloak.client-secret}")
    private val clientSecret: String,
    @Value("\${app.keycloak.grant-type}")
    private val grantType: String,
    @Value("\${app.keycloak.client-id}")
    private val clientId: String
) {
    fun login(request: LoginRequest): ResponseEntity<LoginResponse> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        val map: MultiValueMap<String, String> = LinkedMultiValueMap()
        map.add("username", request.username)
        map.add("password", request.password)
        map.add("client_id", clientId)
        map.add("client_secret", clientSecret)
        map.add("grant_type", grantType)
        val httpEntity = HttpEntity(map, headers)
        val loginResponse = restTemplate.postForEntity(
            loginUrl,
            httpEntity,
            LoginResponse::class.java
        )
        return ResponseEntity
            .status(200)
            .body(loginResponse.body)
    }
}
