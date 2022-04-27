package com.vaskoj.keycloakauth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KeycloakAuthApplication

fun main(args: Array<String>) {
	runApplication<KeycloakAuthApplication>(*args)
}
