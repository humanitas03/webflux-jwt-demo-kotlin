package com.example.reactive.jwt.demo.configuration

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.transaction.ReactiveTransactionManager

@Configuration
@EnableR2dbcRepositories
class R2DbcConnectionFactory : AbstractR2dbcConfiguration() {

    @Bean
    @Primary
    override fun connectionFactory(): ConnectionFactory {
        return ConnectionFactories
            .get("r2dbc:mysql://jay:1234@localhost:3310/mydb?useUnicode=true&characterEncoding=utf8&useSSL=false")
    }

    @Bean
    fun transactionManager(connectionFactory: ConnectionFactory): ReactiveTransactionManager = R2dbcTransactionManager(connectionFactory)
}
