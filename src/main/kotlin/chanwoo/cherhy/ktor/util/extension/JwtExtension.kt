package chanwoo.cherhy.ktor.util.extension

import chanwoo.cherhy.ktor.domain.customer.CustomerId
import chanwoo.cherhy.ktor.domain.customer.CustomerName
import chanwoo.cherhy.ktor.util.property.SecurityProperty.CUSTOMER_ID
import chanwoo.cherhy.ktor.util.property.SecurityProperty.CUSTOMER_NAME
import io.ktor.server.auth.jwt.*

val JWTPrincipal?.customerName: CustomerName
    get() = this?.payload?.getClaim(CUSTOMER_NAME)?.asString()?.let(CustomerName::of)
        ?: throw IllegalArgumentException("Invalid customer name")

val JWTPrincipal?.customerId: CustomerId
    get() = this?.payload?.getClaim(CUSTOMER_ID)?.asLong()?.let(CustomerId::of)
        ?: throw IllegalArgumentException("Invalid user id")

val JWTPrincipal?.isExpired: Boolean
    get() = this?.expiresAt?.time?.let { it < System.currentTimeMillis() }
        ?: throw IllegalArgumentException("Invalid expire time")

val JWTPrincipal?.expireTime: Long
    get() = this?.expiresAt?.time?.minus(System.currentTimeMillis())
        ?: throw IllegalArgumentException("Invalid expire time")
