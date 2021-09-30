package com.example.reactive.jwt.demo.controller

import com.example.reactive.jwt.demo.dto.AuthRequest
import com.example.reactive.jwt.demo.dto.AuthResponse
import com.example.reactive.jwt.demo.jwt.JWTUtil
import com.example.reactive.jwt.demo.jwt.PBKDF2Encoder
import com.example.reactive.jwt.demo.service.UserService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@RestController
class AuthenticationController @Autowired constructor(
    val jwtUtil: JWTUtil,
    val passwordEncoder: PBKDF2Encoder,
    val userService: UserService
) {

    @PostMapping("/api/login")
    fun login(@RequestBody authRequest: AuthRequest): Mono<ResponseEntity<AuthResponse>> = userService
        .findByUsername(authRequest.username)
        .filter { passwordEncoder.encode(authRequest.password) == it.password }
        .map { ResponseEntity.ok(AuthResponse(jwtUtil.generateToken(it))) }
        .switchIfEmpty { Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()) }
}
