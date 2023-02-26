package ch.ksh.iotapi.service

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestService {
    @GetMapping("/test")
    fun testMessage(): String {
        return "This is a test response."
    }
}