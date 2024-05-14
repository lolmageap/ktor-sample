package chanwoo.cherhy.ktor.config

import chanwoo.cherhy.ktor.util.ApplicationConfigUtils.getJwt
import chanwoo.cherhy.ktor.util.property.JwtProperty.AUDIENCE
import chanwoo.cherhy.ktor.util.property.JwtProperty.ISSUER
import chanwoo.cherhy.ktor.util.property.JwtProperty.REALM
import chanwoo.cherhy.ktor.util.property.JwtProperty.SECRET
import chanwoo.cherhy.ktor.util.property.SecurityProperty.AUTHORITY
import chanwoo.cherhy.ktor.util.property.SecurityProperty.CUSTOMER_NAME
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureJwt() {
    val jwtSecret = getJwt(SECRET)
    val jwtIssuer = getJwt(ISSUER)
    val jwtAudience = getJwt(AUDIENCE)
    val jwtRealm = getJwt(REALM)

    install(Authentication) {
        jwt(AUTHORITY) {
            realm = jwtRealm
            verifier(
                JWT.require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIssuer)
                    .build()
            )

            validate { credential ->
                if (credential.payload.getClaim(CUSTOMER_NAME).asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }

            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}