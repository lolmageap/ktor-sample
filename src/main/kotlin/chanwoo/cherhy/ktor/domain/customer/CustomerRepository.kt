package chanwoo.cherhy.ktor.domain.customer

import chanwoo.cherhy.ktor.api.CustomerRequest
import chanwoo.cherhy.ktor.api.CustomerResponse
import chanwoo.cherhy.ktor.util.PageRequest
import org.jetbrains.exposed.sql.selectAll

interface CustomerRepository {
    fun save(request: CustomerRequest, encodedPassword: String)
    fun findAll(request: PageRequest): List<CustomerResponse>
    fun findById(id: Long): CustomerResponse
    fun update(id: Long, request: CustomerRequest)
    fun delete(id: Long)
    fun findByUsername(username: String): CustomerResponse
    fun findAllByIdIn(ids: List<Long>): List<CustomerResponse>
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

    override fun findByUsername(username: String) =
        Customer.find { Customers.email eq username }
            .firstOrNull()
            ?.let(CustomerResponse::of)
            ?: throw IllegalArgumentException("Customer not found")

    override fun findAllByIdIn(ids: List<Long>): List<CustomerResponse> {
        return Customer.find { Customers.id inList ids }
            .map(CustomerResponse::of)
    }
}