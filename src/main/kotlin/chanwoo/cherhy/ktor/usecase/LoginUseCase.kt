package chanwoo.cherhy.ktor.usecase

import chanwoo.cherhy.ktor.api.CustomerRequest
import chanwoo.cherhy.ktor.api.LoginRequest
import chanwoo.cherhy.ktor.domain.customer.CustomerService
import chanwoo.cherhy.ktor.util.JwtManager
import chanwoo.cherhy.ktor.util.extension.matches
import java.util.*

class LoginUseCase(
    private val customerService: CustomerService,
    private val jwtManager: JwtManager,
    private val passwordEncoder: Base64.Encoder,
) {
    fun execute(
        request: LoginRequest,
    ): String {
        val customer = customerService.get(request.email)
        passwordEncoder.matches(request.password, customer.password)

        return jwtManager.createToken(customer.id, request.email)
    }

    fun execute(
        request: CustomerRequest,
    ): String {
        val customer = customerService.get(request.email)
        passwordEncoder.matches(request.password, customer.password)

        return jwtManager.createToken(customer.id, request.email)
    }
}
