package com.vaskoj.keycloakauth.web.response

import lombok.Data

@Data
class LoginResponse(
   val access_token: String,
   val refresh_token: String,
   val expires_in: String,
   val refresh_expires_in: String,
   val token_type: String
)