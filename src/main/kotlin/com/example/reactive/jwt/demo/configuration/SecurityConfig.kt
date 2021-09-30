package com.example.reactive.jwt.demo.configuration

import com.example.reactive.jwt.demo.repository.SecurityContextRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import reactor.core.publisher.Mono

/*
@Configuration
class CustomAuthenticationManagerBean constructor(
    val passwordEncoder: PasswordEncoder,
    val userService: UserService
) {
    @Bean
    fun authenticationManager(): ReactiveAuthenticationManager {
        val authManager = UserDetailsRepositoryReactiveAuthenticationManager(userService) // TODO: MapReactiveUserDetailsService 를 구현하도록 한다.
        UserDetailsRepositoryReactiveAuthenticationManager()
        authManager.setPasswordEncoder(passwordEncoder)
        return authManager
    }
}
*/

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig @Autowired constructor(
    val authenticationManager: CustomAuthenticationManager,
    val securityContextRepository: SecurityContextRepository
) {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .exceptionHandling()
            .authenticationEntryPoint { exchange, _ -> Mono.fromRunnable { exchange.response.statusCode = HttpStatus.UNAUTHORIZED } }
            .accessDeniedHandler { exchange, _ -> Mono.fromRunnable { exchange.response.statusCode = HttpStatus.FORBIDDEN } }
            .and()
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .authenticationManager(authenticationManager)
            .securityContextRepository(securityContextRepository)
            .authorizeExchange()
            .pathMatchers("/api/login").permitAll()
            .anyExchange().authenticated()
            .and()
            .build()
    }
}

@Configuration
@EnableWebFlux
class CORSFilter : WebFluxConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*")
    }
}
