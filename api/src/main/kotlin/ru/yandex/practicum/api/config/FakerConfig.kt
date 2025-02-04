package ru.yandex.practicum.api.config

import com.github.javafaker.Faker
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FakerConfig {

    @Bean
    fun faker(): Faker = Faker()
}