package booookstore.playground.springmyplaygroundsecurityloginjson

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JsonLoginAuthenticationFilter(authenticationManager: AuthenticationManager) :
    UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        if (request.method != "POST") {
            throw AuthenticationServiceException("Authentication method not supported: " + request.method)
        }
        val loginRequest = jacksonObjectMapper().readValue(request.inputStream, LoginRequestJSON::class.java)
        var username = loginRequest.username
        username = username.trim { it <= ' ' } ?: ""
        var password = loginRequest.password
        password = password ?: ""
        val authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password)
        setDetails(request, authRequest)
        return authenticationManager.authenticate(authRequest)
    }

}

data class LoginRequestJSON(val username: String, val password: String)