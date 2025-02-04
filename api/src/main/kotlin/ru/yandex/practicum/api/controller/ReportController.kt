package ru.yandex.practicum.api.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import ru.yandex.practicum.api.service.ReportService

@RestController
@CrossOrigin
class ReportController(private val reportService: ReportService) {

    @GetMapping("/reports")
    fun getReports(): List<String> = reportService.getReports()
}