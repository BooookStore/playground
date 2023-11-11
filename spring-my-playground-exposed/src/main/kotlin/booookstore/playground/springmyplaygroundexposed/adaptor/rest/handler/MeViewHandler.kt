package booookstore.playground.springmyplaygroundexposed.adaptor.rest.handler

import arrow.core.None
import arrow.core.Some
import booookstore.playground.springmyplaygroundexposed.query.MeViewQuery
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.notFound
import org.springframework.web.servlet.function.ServerResponse.ok
import java.util.*

@Component
@Transactional(readOnly = true)
class MeViewHandler(private val meViewQuery: MeViewQuery) {

    fun meView(serverRequest: ServerRequest): ServerResponse {
        val authentication = SecurityContextHolder.getContext().authentication!!
        val userId = UUID.fromString(authentication.name)
        return when (val meViewOption = meViewQuery.findMeById(userId)) {
            None -> notFound().build()
            is Some -> ok().contentType(APPLICATION_JSON).body(meViewOption.value)
        }
    }

}