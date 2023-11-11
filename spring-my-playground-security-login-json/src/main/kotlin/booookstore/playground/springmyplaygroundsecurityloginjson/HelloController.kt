package booookstore.playground.springmyplaygroundsecurityloginjson

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping("/hello")
    fun hello(): HelloResponse {
        return HelloResponse("hello")
    }

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }

}

data class HelloResponse(val message: String)