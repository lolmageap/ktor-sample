package chanwoo.cherhy.ktor.domain.customer

import chanwoo.cherhy.ktor.api.CustomerRequest
import chanwoo.cherhy.ktor.util.model.PageRequest

class CustomerService(
    private val customerRepository: CustomerRepository,
) {
    fun save(
        request: CustomerRequest,
        encodedPassword: String,
    ) = customerRepository.save(request, encodedPassword)

    fun getAll(request: PageRequest) = customerRepository.findAll(request)

    fun get(id: CustomerId) = customerRepository.findById(id)

    fun getAll(ids: List<CustomerId>) = customerRepository.findAllByIdIn(ids)

    fun get(username: String) = customerRepository.findByUsername(username)

    fun update(id: CustomerId, request: CustomerRequest) = customerRepository.update(id, request)

    fun delete(id: CustomerId) = customerRepository.delete(id)
}