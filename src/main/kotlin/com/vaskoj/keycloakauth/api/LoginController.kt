package com.vaskoj.keycloakauth.api

import com.vaskoj.keycloakauth.service.LoginService
import com.vaskoj.keycloakauth.web.request.LoginRequest
import com.vaskoj.keycloakauth.web.response.LoginResponse
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping("/api/v1/public/")
@Slf4j
class LoginController(private val loginService: LoginService) {


    @PostMapping("login")
    fun login(
        request: HttpServletRequest,
        @RequestBody loginRequest: LoginRequest
    ): ResponseEntity<LoginResponse> {
        return loginService.login(loginRequest);
    }

    @GetMapping("logout")
    fun logout(request: HttpServletRequest) {
        request.logout()
    }
}

