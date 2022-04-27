package com.vaskoj.keycloakauth.web.request

import lombok.Data

@Data
class LoginRequest(
    val username: String,
    val password: String
)