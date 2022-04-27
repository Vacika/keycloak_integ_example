package com.vaskoj.keycloakauth.config.handler

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class RestAccessDeniedHandler: AccessDeniedHandler {

    @Throws(IOException::class, ServletException::class)
    override fun handle(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        e: AccessDeniedException
    ) {
        httpServletResponse.sendError(
            HttpServletResponse.SC_FORBIDDEN,
            e.message
        )
    }
}