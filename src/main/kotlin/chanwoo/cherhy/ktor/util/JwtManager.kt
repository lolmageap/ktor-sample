package chanwoo.cherhy.ktor.util

import chanwoo.cherhy.ktor.util.SecurityProperty.USERNAME
import chanwoo.cherhy.ktor.util.SecurityProperty.CUSTOMER_ID
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class JwtManager {
    fun createToken(
        userid: Long,
        username: String,
    ) =
        JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim(CUSTOMER_ID, userid)
            .withClaim(USERNAME, username)
            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
            .sign(Algorithm.HMAC256(secret))!!

    companion object {
        private val secret = ApplicationConfigUtils.getJwt("secret")
        private val issuer = ApplicationConfigUtils.getJwt("issuer")
        private val audience = ApplicationConfigUtils.getJwt("audience")
    }
}