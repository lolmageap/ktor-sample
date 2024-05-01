package chanwoo.cherhy.ktor.util

import chanwoo.cherhy.ktor.util.SecurityProperty.USERNAME
import io.ktor.server.auth.jwt.*

val JWTPrincipal?.username: String
    get() = this?.payload?.getClaim(USERNAME)?.asString()
        ?: throw IllegalArgumentException("Invalid username")
