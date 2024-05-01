package chanwoo.cherhy.ktor.config

import chanwoo.cherhy.ktor.util.ApplicationConfigUtils.getJwt
import chanwoo.cherhy.ktor.util.SecurityProperty.AUTHORITY
import chanwoo.cherhy.ktor.util.SecurityProperty.USERNAME
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureJwt() {
    val jwtSecret = getJwt("secret")
    val jwtIssuer = getJwt("issuer")
    val jwtAudience = getJwt("audience")
    val jwtRealm = getJwt("realm")

    authentication {
        jwt(AUTHORITY) {
            realm = jwtRealm
            verifier(
                JWT.require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIssuer)
                    .build()
            )
        }
        jwt(AUTHORITY) {
            validate { credential ->
                if (credential.payload.getClaim(USERNAME).asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
        jwt(AUTHORITY) {
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}