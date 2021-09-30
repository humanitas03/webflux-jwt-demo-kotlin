package com.example.reactive.jwt.demo.configuration

import com.example.reactive.jwt.demo.jwt.JWTUtil
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CustomAuthenticationManager constructor(
    private val jwtUtil: JWTUtil
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication>? {
        val authToken: String = authentication.credentials.toString()
        val username = jwtUtil!!.getUsernameFromToken(authToken)
        return Mono.just(jwtUtil.validateToken(authToken))
            .filter { valid: Boolean? -> valid!! }
            .switchIfEmpty(Mono.empty())
            .map {
                val claims = jwtUtil.getAllClaimsFromToken(authToken)
                val rolesMap: List<String> = claims.get("role", List::class.java) as List<String> // TODO: class casting만 이 답일가...?
                UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    rolesMap.map { SimpleGrantedAuthority(it) }
                        .toList()
                )
            }
    }
}
