package chanwoo.cherhy.plugins.domain.customer

import chanwoo.cherhy.plugins.api.CustomerRequest
import chanwoo.cherhy.plugins.api.CustomerResponse
import chanwoo.cherhy.plugins.util.PageRequest
import org.jetbrains.exposed.sql.selectAll

interface CustomerRepository {
    fun save(request: CustomerRequest, encodedPassword: String)
    fun findAll(request: PageRequest): List<CustomerResponse>
    fun findById(id: Long): CustomerResponse
    fun update(id: Long, request: CustomerRequest)
    fun delete(id: Long)
}

class CustomerRepositoryImpl: CustomerRepository {
    override fun save(
        request: CustomerRequest,
        encodedPassword: String,
    ) {
        Customer.new {
            name = request.name
            email = request.email
            password = encodedPassword
            phoneNumber = request.phoneNumber
        }
    }

    override fun findAll(request: PageRequest) =
        Customers.selectAll()
            .limit(request.size, request.offset)
            .map(Customer::wrapRow)
            .map(CustomerResponse::of)

    override fun findById(id: Long) =
        Customer.findById(id)
            ?.let(CustomerResponse::of)
            ?: throw IllegalArgumentException("Customer not found")

    override fun update(id: Long, request: CustomerRequest) {
        Customer.findById(id)?.apply {
            name = request.name
            email = request.email
            phoneNumber = request.phoneNumber
        } ?: throw IllegalArgumentException("Customer not found")
    }

    override fun delete(id: Long) {
        Customer.findById(id)?.delete()
    }
}