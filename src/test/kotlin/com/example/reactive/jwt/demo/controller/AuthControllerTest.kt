package com.example.reactive.jwt.demo.controller

import com.example.reactive.jwt.demo.dto.AuthResponse
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    @DisplayName("Jwt의 토큰 생성 테스트")
    fun tokenGenerationTest() {
        val testRequest = """
            {
            	"username":"admin",
            	"password":"admin"
            }
        """.trimIndent()

        val token = webTestClient
            .post()
            .uri("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(testRequest)
            .exchange()
            .expectStatus().isOk
            .returnResult(AuthResponse::class.java)
            .responseBody
            .blockFirst()

        println("GET TOKEN : ${token?.token}")
    }
}
