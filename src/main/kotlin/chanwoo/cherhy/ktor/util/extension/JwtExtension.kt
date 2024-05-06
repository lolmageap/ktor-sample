package chanwoo.cherhy.ktor.util.extension

import chanwoo.cherhy.ktor.util.CustomerId
import chanwoo.cherhy.ktor.util.CustomerName
import chanwoo.cherhy.ktor.util.property.SecurityProperty.CUSTOMER_NAME
import chanwoo.cherhy.ktor.util.property.SecurityProperty.CUSTOMER_ID
import io.ktor.server.auth.jwt.*

val JWTPrincipal?.customerName: CustomerName
    get() = this?.payload?.getClaim(CUSTOMER_NAME)?.asString()
        ?: throw IllegalArgumentException("Invalid customer name")

val JWTPrincipal?.customerId: CustomerId
    get() = this?.payload?.getClaim(CUSTOMER_ID)?.asLong()
        ?: throw IllegalArgumentException("Invalid user id")

val JWTPrincipal?.isExpired: Boolean
    get() = this?.expiresAt?.time?.let { it < System.currentTimeMillis() }
        ?: throw IllegalArgumentException("Invalid expire time")

val JWTPrincipal?.expireTime: Long
    get() = this?.expiresAt?.time?.minus(System.currentTimeMillis())
        ?: throw IllegalArgumentException("Invalid expire time")
