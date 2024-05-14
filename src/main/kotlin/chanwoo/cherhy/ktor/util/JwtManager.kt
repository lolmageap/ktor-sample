package chanwoo.cherhy.ktor.util

import chanwoo.cherhy.ktor.domain.customer.CustomerId
import chanwoo.cherhy.ktor.domain.customer.CustomerName
import chanwoo.cherhy.ktor.util.property.JwtProperty.AUDIENCE
import chanwoo.cherhy.ktor.util.property.JwtProperty.ISSUER
import chanwoo.cherhy.ktor.util.property.JwtProperty.SECRET
import chanwoo.cherhy.ktor.util.property.SecurityProperty.CUSTOMER_ID
import chanwoo.cherhy.ktor.util.property.SecurityProperty.CUSTOMER_NAME
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class JwtManager {
    fun createToken(
        customerId: CustomerId,
        customerName: CustomerName,
    ) =
        JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim(CUSTOMER_ID, customerId)
            .withClaim(CUSTOMER_NAME, customerName)
            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
            .sign(Algorithm.HMAC256(secret))!!

    companion object {
        private val secret = ApplicationConfigUtils.getJwt(SECRET)
        private val issuer = ApplicationConfigUtils.getJwt(ISSUER)
        private val audience = ApplicationConfigUtils.getJwt(AUDIENCE)
    }
}