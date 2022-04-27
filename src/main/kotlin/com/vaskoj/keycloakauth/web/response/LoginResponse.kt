package com.vaskoj.keycloakauth.web.response

import lombok.Data

@Data
class LoginResponse(
    private val access_token: String,
    private val refresh_token: String,
    private val expires_in: String,
    private val refresh_expires_in: String,
    private val token_type: String
)