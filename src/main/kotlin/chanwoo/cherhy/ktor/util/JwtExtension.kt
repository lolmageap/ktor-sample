package chanwoo.cherhy.ktor.util

import chanwoo.cherhy.ktor.util.SecurityProperty.USERNAME
import chanwoo.cherhy.ktor.util.SecurityProperty.USER_ID
import io.ktor.server.auth.jwt.*

val JWTPrincipal?.username: String
    get() = this?.payload?.getClaim(USERNAME)?.asString()
        ?: throw IllegalArgumentException("Invalid username")

val JWTPrincipal?.userId: Long
    get() = this?.payload?.getClaim(USER_ID)?.asLong()
        ?: throw IllegalArgumentException("Invalid userId")

val JWTPrincipal?.isExpired: Boolean
    get() = this?.expiresAt?.time?.let { it < System.currentTimeMillis() }
        ?: throw IllegalArgumentException("Invalid expire time")

val JWTPrincipal?.expireTime: Long
    get() = this?.expiresAt?.time?.minus(System.currentTimeMillis())
        ?: throw IllegalArgumentException("Invalid expire time")
