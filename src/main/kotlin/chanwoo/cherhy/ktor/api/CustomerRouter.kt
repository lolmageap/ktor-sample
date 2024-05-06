package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.domain.customer.CustomerService
import chanwoo.cherhy.ktor.usecase.LoginUseCase
import chanwoo.cherhy.ktor.usecase.SignupUseCase
import chanwoo.cherhy.ktor.util.property.EndPoint.CUSTOMER.DELETE_CUSTOMER
import chanwoo.cherhy.ktor.util.property.EndPoint.CUSTOMER.GET_ME
import chanwoo.cherhy.ktor.util.property.EndPoint.CUSTOMER.LOGIN
import chanwoo.cherhy.ktor.util.property.EndPoint.CUSTOMER.SIGN_UP
import chanwoo.cherhy.ktor.util.property.EndPoint.CUSTOMER.UPDATE_CUSTOMER
import chanwoo.cherhy.ktor.util.property.SecurityProperty.AUTHORITY
import chanwoo.cherhy.ktor.util.extension.jwt
import chanwoo.cherhy.ktor.util.extension.customerId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.customer() {
    val customerService: CustomerService by inject()
    val loginUseCase: LoginUseCase by inject()
    val signupUseCase: SignupUseCase by inject()

    post(LOGIN) {
        val request = call.receive<LoginRequest>()
        val jwt = loginUseCase.execute(request)
        headers { append(HttpHeaders.Authorization, jwt) }
        call.respond(HttpStatusCode.OK, jwt)
    }

    post(SIGN_UP) {
        val request = call.receive<CustomerRequest>()
        signupUseCase.execute(request)
        val jwt = loginUseCase.execute(request)
        headers { append(HttpHeaders.Authorization, jwt) }
        call.respond(HttpStatusCode.Created, jwt)
    }

    authenticate(AUTHORITY) {
        get(GET_ME) {
            val customerId = call.jwt.customerId
            val customer = customerService.get(customerId)
            call.respond(HttpStatusCode.OK, customer)
        }
    }

    authenticate(AUTHORITY) {
        put(UPDATE_CUSTOMER) {
            val customerId = call.jwt.customerId
            val request = call.receive<CustomerRequest>()
            customerService.update(customerId, request)
            call.respond(HttpStatusCode.Created)
        }
    }

    authenticate(AUTHORITY) {
        delete(DELETE_CUSTOMER) {
            val customerId = call.jwt.customerId
            customerService.delete(customerId)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}