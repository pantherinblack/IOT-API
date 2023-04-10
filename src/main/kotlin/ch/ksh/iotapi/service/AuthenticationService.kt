package ch.ksh.iotapi.service

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie


@RestController
@RequestMapping("/auth")
class AuthenticationService {

    @ResponseBody
    @GetMapping("/login")
    fun login(
        @RequestParam("userName") userName: String,
        @RequestParam("password") password: String
    ): ResponseEntity<String?> {
        //TODO check if real account
        val userNameCookie = Cookie("userName",userName).apply { maxAge = 604800 } //One Week
        val passwordCookie = Cookie("password",password).apply { maxAge = 604800 }
        val header = HttpHeaders()
        header.add(HttpHeaders.SET_COOKIE, userNameCookie.toString())
        header.add(HttpHeaders.SET_COOKIE, passwordCookie.toString())
        return ResponseEntity.status(200).headers(header).body(null)
    }

    @ResponseBody
    @DeleteMapping("/logout")
    fun logout(): ResponseEntity<String> {
        val header = HttpHeaders()
        val userNameCookie = Cookie("userName","").apply { maxAge = 1 }
        val passwordCookie = Cookie("password","").apply { maxAge = 1 }
        header.add(HttpHeaders.SET_COOKIE, userNameCookie.toString())
        header.add(HttpHeaders.SET_COOKIE, passwordCookie.toString())
        return ResponseEntity.status(200).headers(header).body(null)

    }
}