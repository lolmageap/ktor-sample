package chanwoo.cherhy.ktor.domain.customer

import chanwoo.cherhy.ktor.api.CustomerRequest
import chanwoo.cherhy.ktor.api.CustomerResponse
import chanwoo.cherhy.ktor.util.model.PageRequest
import org.jetbrains.exposed.sql.selectAll

interface CustomerRepository {
    fun save(
        request: CustomerRequest,
        encodedPassword: String,
    ): CustomerResponse

    fun update(
        id: CustomerId,
        request: CustomerRequest,
    ): CustomerResponse

    fun delete(
        id: CustomerId,
    )

    fun findAll(
        request: PageRequest,
    ): List<CustomerResponse>

    fun findById(
        id: CustomerId,
    ): CustomerResponse

    fun findByUsername(
        username: String,
    ): CustomerResponse

    fun findAllByIdIn(
        ids: List<CustomerId>,
    ): List<CustomerResponse>
}

class CustomerRepositoryImpl : CustomerRepository {
    override fun save(
        request: CustomerRequest,
        encodedPassword: String,
    ) =
        Customer.new {
            name = request.name
            email = request.email
            password = encodedPassword
            phoneNumber = request.phoneNumber
        }.let(CustomerResponse::of)

    override fun findAll(
        request: PageRequest,
    ) =
        Customers.selectAll()
            .limit(request.size, request.offset)
            .map(Customer::wrapRow)
            .map(CustomerResponse::of)

    override fun findById(
        id: Long,
    ) =
        Customer.findById(id)
            ?.let(CustomerResponse::of)
            ?: throw IllegalArgumentException("Customer not found")

    override fun update(
        id: CustomerId,
        request: CustomerRequest,
    ) =
        Customer.findById(id)?.apply {
            name = request.name
            email = request.email
            phoneNumber = request.phoneNumber
        }
            ?.let(CustomerResponse::of)
            ?: throw IllegalArgumentException("Customer not found")

    override fun delete(
        id: CustomerId,
    ) { Customer.findById(id)?.delete() }

    override fun findByUsername(
        username: String,
    ) =
        Customer.find { Customers.email eq username }
            .firstOrNull()
            ?.let(CustomerResponse::of)
            ?: throw IllegalArgumentException("Customer not found")

    override fun findAllByIdIn(
        ids: List<Long>,
    ): List<CustomerResponse> =
        Customer.find { Customers.id inList ids }
            .map(CustomerResponse::of)
}