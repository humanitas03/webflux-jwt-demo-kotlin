//package com.example.reactive.jwt.demo.repository
//
//import org.springframework.data.annotation.Id
//import org.springframework.security.core.userdetails.UserDetails
//
//data class User (
//
//    @Id
//    var userId: Long? = null,
//    val username: String? = null,
//    val password: String? = null,
//    val nickname: String? = null,
//    val activated: Boolean = false,
//    val authority: String? = null,
//) : UserDetails {
//
//
//
//
//    override fun isAccountNonExpired(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun isAccountNonLocked(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun isCredentialsNonExpired(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun isEnabled(): Boolean {
//        TODO("Not yet implemented")
//    }
//}