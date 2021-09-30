package com.example.reactive.jwt.demo.repository

import com.example.reactive.jwt.demo.configuration.CustomAuthenticationManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class SecurityContextRepository @Autowired constructor(
    val authenticationManager: CustomAuthenticationManager
) : ServerSecurityContextRepository {
    override fun save(exchange: ServerWebExchange?, context: SecurityContext?): Mono<Void> {
        throw UnsupportedOperationException("미구현입니다.")
    }

    override fun load(exchange: ServerWebExchange?): Mono<SecurityContext> {
        return Mono.justOrEmpty(exchange?.request?.headers?.getFirst(HttpHeaders.AUTHORIZATION))
            .filter { authHeader -> authHeader.startsWith("Bearer ") }
            .flatMap { authHeader ->
                val authToken = authHeader.substring(7)
                val auth = UsernamePasswordAuthenticationToken(authToken, authToken)
                this.authenticationManager.authenticate(auth)?.map { SecurityContextImpl(it) } ?: error(
                    NoSuchElementException()
                )
            }
    }
}
