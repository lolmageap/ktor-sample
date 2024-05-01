package chanwoo.cherhy.ktor.usecase

import chanwoo.cherhy.ktor.api.CustomerRequest
import chanwoo.cherhy.ktor.domain.customer.CustomerService
import chanwoo.cherhy.ktor.util.encode
import java.util.*

class SignupUseCase(
    private val customerService: CustomerService,
    private val passwordEncoder: Base64.Encoder,
) {
    fun execute(request: CustomerRequest) {
        val encodedPassword = passwordEncoder.encode(request.password)
        customerService.save(request, encodedPassword)
    }
}
