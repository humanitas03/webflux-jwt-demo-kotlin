//package com.example.reactive.jwt.demo.repository
//
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import reactor.test.StepVerifier
//import java.time.Duration
//
//@SpringBootTest
//class UserRepositoryTest @Autowired constructor(
//    val userRepository: UserRepository
//) {
//
//    @Test
//    fun insertTest() {
//        userRepository
//            .save(User(null, "jay", "123", "hwang", false, "USER"))
//            .block(Duration.ofSeconds(2))
//
//        userRepository.findAll()
//            .`as`(StepVerifier::create)
//            .expectNextCount(1)
//            .verifyComplete()
//    }
//}
