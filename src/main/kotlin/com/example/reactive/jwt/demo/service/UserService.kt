package com.example.reactive.jwt.demo.service

import com.example.reactive.jwt.demo.entity.Role
import com.example.reactive.jwt.demo.entity.User
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct

// R2DBC 연동이 준비되지 않아 임시로 유저를 로드하는 정보로 셋업
@Service
class UserService {

    lateinit var data: Map<String, User>

    @PostConstruct
    fun init() {
        // TODO : application yml에 Secret을 바꿧기 때문에 테스를 위한 Hash 스트링은 참고한 사이트와 다릅니다.
        val user1 = User(
            "jay",
            "UGufPqllFS9NQf7g4wmtLq56QpxC/PxBDm+TYK1sIzg=",
            true, mutableListOf(Role.ROLE_USER)
        )

        val user2 = User(
            "admin",
            "/VLE17rkic1aJ5EuD13F00eTz+jUbBm4tSc6wIzjHF0=",
            true, mutableListOf(Role.ROLE_ADMIN)
        )

        data = mapOf("jay" to user1, "admin" to user2)
    }

    fun findByUsername(username: String): Mono<User> {
        return Mono.justOrEmpty(data[username])
    }
}
