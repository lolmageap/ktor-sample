package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.domain.customer.CustomerService
import chanwoo.cherhy.ktor.usecase.LoginUseCase
import chanwoo.cherhy.ktor.usecase.SignupUseCase
import chanwoo.cherhy.ktor.util.EndPoint.CUSTOMER.DELETE_CUSTOMER
import chanwoo.cherhy.ktor.util.EndPoint.CUSTOMER.GET_ME
import chanwoo.cherhy.ktor.util.EndPoint.CUSTOMER.LOGIN
import chanwoo.cherhy.ktor.util.EndPoint.CUSTOMER.SIGN_UP
import chanwoo.cherhy.ktor.util.EndPoint.CUSTOMER.UPDATE_CUSTOMER
import chanwoo.cherhy.ktor.util.SecurityProperty.AUTHORITY
import chanwoo.cherhy.ktor.util.jwt
import chanwoo.cherhy.ktor.util.userId
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
        call.respond(jwt)
        call.respond(HttpStatusCode.OK)
    }

    post(SIGN_UP) {
        val request = call.receive<CustomerRequest>()
        signupUseCase.execute(request)
        val jwt = loginUseCase.execute(request)
        call.respond(jwt)
        call.respond(HttpStatusCode.Created)
    }

    authenticate(AUTHORITY) {
        get(GET_ME) {
            val customerId = call.jwt.userId
            val customer = customerService.get(customerId)
            call.respond(customer)
            call.respond(HttpStatusCode.OK)
        }
    }

    authenticate(AUTHORITY) {
        put(UPDATE_CUSTOMER) {
            val customerId = call.jwt.userId
            val request = call.receive<CustomerRequest>()
            customerService.update(customerId, request)
            call.respond(HttpStatusCode.Created)
        }
    }

    authenticate(AUTHORITY) {
        delete(DELETE_CUSTOMER) {
            val customerId = call.jwt.userId
            customerService.delete(customerId)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}