package chanwoo.cherhy.plugins.customer.repository

import chanwoo.cherhy.plugins.customer.entity.Customer
import chanwoo.cherhy.plugins.customer.entity.Customers
import chanwoo.cherhy.plugins.customer.request.CustomerRequest
import chanwoo.cherhy.plugins.customer.response.CustomerResponse
import chanwoo.cherhy.plugins.util.PageRequest
import org.jetbrains.exposed.sql.selectAll

interface CustomerRepository {
    fun save(request: CustomerRequest, encodedPassword: String)
    fun getAll(request: PageRequest): List<CustomerResponse>
    fun get(id: Long): CustomerResponse
}

object CustomerRepositoryImpl: CustomerRepository {
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

    override fun getAll(request: PageRequest) =
        Customers.selectAll()
            .limit(request.size, request.offset)
            .map(Customer::wrapRow)
            .map(CustomerResponse::of)

    override fun get(id: Long) =
        Customer.findById(id)
            ?.let(CustomerResponse::of)
            ?: throw IllegalArgumentException("Customer not found")
}