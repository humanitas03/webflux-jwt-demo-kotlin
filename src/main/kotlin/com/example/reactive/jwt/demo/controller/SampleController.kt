package com.example.reactive.jwt.demo.controller

import com.example.reactive.jwt.demo.dto.Message
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
class SampleController {
    @GetMapping("/api/user")
    @PreAuthorize("hasRole('USER')")
    fun user(): Mono<Message>? {
        return Mono.just(Message("Content for user"))
    }

    @GetMapping("/api/admin")
    @PreAuthorize("hasRole('ADMIN')")
    fun admin(): Mono<Message>? {
        return Mono.just(Message("Content for admin"))
    }

    @GetMapping("/api/user-or-admin")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    fun userOrAdmin(): Mono<Message>? {
        return Mono.just(Message("Content for user or admin"))
    }
}