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
    fun listDevices(
        @RequestParam("userName") userName: String,
        @RequestParam("password") password: String
    ): ResponseEntity<String?> {
        //check if real account
        val userNameCookie = Cookie("userName",userName)
        val passwordCookie = Cookie("password",password)
        val header = HttpHeaders()
        header.add(HttpHeaders.SET_COOKIE, userNameCookie.toString())
        header.add(HttpHeaders.SET_COOKIE, passwordCookie.toString())
        return ResponseEntity.status(200).headers(header).body(null)
    }
}