package ru.yandex.practicum.api.service.impl

import com.github.javafaker.Faker
import org.springframework.stereotype.Service
import ru.yandex.practicum.api.service.ReportService

@Service
class ReportServiceImpl(private val faker: Faker) : ReportService {
    override fun getReports(): List<String> {
        return (0..10).map { faker.name().fullName() }
    }
}