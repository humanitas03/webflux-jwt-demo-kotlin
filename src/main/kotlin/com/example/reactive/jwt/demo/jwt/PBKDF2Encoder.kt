package com.example.reactive.jwt.demo.jwt

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.lang.RuntimeException
import java.security.spec.InvalidKeySpecException
import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import kotlin.NoSuchElementException

private val log = KotlinLogging.logger{}

@Component
class PBKDF2Encoder constructor(
    @Value("\${jwt.secret}")
    val secret: String,

    @Value("\${jwt.iteration}")
    val iteration: Int,

    @Value("\${jwt.keylength}")
    val keylength: Int,

) : PasswordEncoder {
    override fun encode(rawPassword: CharSequence?): String {
        kotlin.runCatching {
            val result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                .generateSecret(PBEKeySpec(rawPassword.toString().toCharArray(), secret.toByteArray(), iteration, keylength))
                .encoded
            log.info("ENCODED : ${Base64.getEncoder().encodeToString(result)}")
            return Base64.getEncoder().encodeToString(result)
        }.onFailure { throwable ->
            when (throwable) {
                is NoSuchElementException, is InvalidKeySpecException -> throw RuntimeException(throwable)
                else -> throw throwable
            }
        }.getOrThrow()
    }

    override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
        return encode(rawPassword) == encodedPassword
    }
}
