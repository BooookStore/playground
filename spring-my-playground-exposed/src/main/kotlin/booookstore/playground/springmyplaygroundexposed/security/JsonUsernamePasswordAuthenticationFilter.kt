package booookstore.playground.springmyplaygroundexposed.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JsonUsernamePasswordAuthenticationFilter(authenticationManager: AuthenticationManager) :
    UsernamePasswordAuthenticationFilter(authenticationManager) {

    data class Json(val username: String, val password: String)

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        if (request.method != "POST") {
            throw AuthenticationServiceException("Authentication method not supported: " + request.method)
        }
        val json = jacksonObjectMapper().readValue<Json>(request.inputStream)
        var username = json.username
        username = username.trim { it <= ' ' }
        val password = json.password
        val authRequest = UsernamePasswordAuthenticationToken.unauthenticated(
            username,
            password
        )
        // Allow subclasses to set the "details" property
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest)
        return authenticationManager.authenticate(authRequest)
    }

}