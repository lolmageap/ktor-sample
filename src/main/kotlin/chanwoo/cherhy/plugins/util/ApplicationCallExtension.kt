package chanwoo.cherhy.plugins.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.http.*
import io.ktor.server.application.*

val mapper = jacksonObjectMapper()

val ApplicationCall.id: Long
    get() = this.parameters["id"]?.toLongOrNull()
        ?: throw IllegalArgumentException("Invalid entity id")

inline fun <reified T : Any> ApplicationCall.getQueryParams(): T {
    return this.request.queryParameters.toClass()
}

inline fun <reified T : Any> Parameters.toClass(): T {
    val map = entries().associate {
        it.key to (it.value.getOrNull(0)
            ?: throw IllegalArgumentException("Missing value for key ${it.key}"))
    }
    return mapper.convertValue(map, T::class.java)
}