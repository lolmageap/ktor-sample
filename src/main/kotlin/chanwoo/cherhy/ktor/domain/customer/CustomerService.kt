package chanwoo.cherhy.ktor.domain.customer

import chanwoo.cherhy.ktor.api.CustomerRequest
import chanwoo.cherhy.ktor.extension.encode
import chanwoo.cherhy.ktor.util.PageRequest
import java.util.Base64.Encoder

class CustomerService(
    private val customerRepository: CustomerRepository,
    private val passwordEncoder: Encoder,
) {
    fun save(request: CustomerRequest) =
        passwordEncoder.encode(request.password).let {
            customerRepository.save(request, it)
        }

    fun getAll(request: PageRequest) = customerRepository.findAll(request)

    fun get(id: Long) = customerRepository.findById(id)

    fun update(id: Long, request: CustomerRequest) = customerRepository.update(id, request)

    fun delete(id: Long) = customerRepository.delete(id)
}