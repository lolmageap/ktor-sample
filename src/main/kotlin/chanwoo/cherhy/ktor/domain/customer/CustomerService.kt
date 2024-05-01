package chanwoo.cherhy.ktor.domain.customer

import chanwoo.cherhy.ktor.api.CustomerRequest
import chanwoo.cherhy.ktor.util.PageRequest

class CustomerService(
    private val customerRepository: CustomerRepository,
) {
    fun save(
        request: CustomerRequest,
        encodedPassword: String,
    ) = customerRepository.save(request, encodedPassword)

    fun getAll(request: PageRequest) = customerRepository.findAll(request)

    fun get(id: Long) = customerRepository.findById(id)

    fun get(username: String) = customerRepository.findByUsername(username)

    fun update(id: Long, request: CustomerRequest) = customerRepository.update(id, request)

    fun delete(id: Long) = customerRepository.delete(id)
}