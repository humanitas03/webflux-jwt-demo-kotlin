package com.example.reactive.jwt.demo.jwt

import com.example.reactive.jwt.demo.entity.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value

import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.annotation.PostConstruct


@Component
class JWTUtil {
    @Value("\${jwt.jjwt.secret}")
    private val secret: String? = null

    @Value("\${jwt.jjwt.expiration}")
    private val expirationTime: String? = null
    private var key: Key? = null
    @PostConstruct
    fun init() {
        key = Keys.hmacShaKeyFor(secret!!.toByteArray())
    }

    fun getAllClaimsFromToken(token: String?): Claims {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
    }

    fun getUsernameFromToken(token: String?): String {
        return getAllClaimsFromToken(token).subject
    }

    fun getExpirationDateFromToken(token: String?): Date {
        return getAllClaimsFromToken(token).expiration
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    fun generateToken(user: User): String {
        val claims: MutableMap<String, Any?> = HashMap()
        claims["role"] = user.roles
        return doGenerateToken(claims, user.username)
    }

    private fun doGenerateToken(claims: Map<String, Any?>, username: String): String {
        val expirationTimeLong = expirationTime!!.toLong() // in second
        val createdDate = Date()
        val expirationDate = Date(createdDate.time + expirationTimeLong * 1000)
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(createdDate)
            .setExpiration(expirationDate)
            .signWith(key)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return !isTokenExpired(token)
    }
}