package ch.ksh.iotapi.service

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Test Service
 * @author Kevin Stupar
 * @since 07.05.2023
 */
@RestController
class TestService {

    /**
     * returns a simple text to verify the server is running
     */
    @GetMapping("/test")
    fun testMessage(): String {
        return "This is a test response."
    }
}