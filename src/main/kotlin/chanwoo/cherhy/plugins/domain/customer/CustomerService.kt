package chanwoo.cherhy.plugins.domain.customer

import chanwoo.cherhy.plugins.api.CustomerRequest
import chanwoo.cherhy.plugins.extension.encode
import chanwoo.cherhy.plugins.util.PageRequest
import java.util.Base64.Encoder

class CustomerService(
    private val customerRepository: CustomerRepository,
    private val passwordEncoder: Encoder,
) {
    fun save(request: CustomerRequest) =
        passwordEncoder.encode(request.password).let {
            customerRepository.save(request, it)
        }

    fun getAll(request: PageRequest) =
        customerRepository.getAll(request)

    fun get(id: Long) =
        customerRepository.get(id)
}