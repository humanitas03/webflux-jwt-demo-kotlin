package com.example.reactive.jwt.demo.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class User constructor(
    private val username: String,
    private val password: String,
    val enabled: Boolean,
    val roles: MutableList<Role>,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles.map{SimpleGrantedAuthority("ROLE_$roles")}.toMutableList()
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return false // 미구현
    }

    override fun isAccountNonLocked(): Boolean {
        return false // 미구현
    }

    override fun isCredentialsNonExpired(): Boolean {
        return false // 미구현
    }

    override fun isEnabled(): Boolean {
        return this.enabled
    }
}
