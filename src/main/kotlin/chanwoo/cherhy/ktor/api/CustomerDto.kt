package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.domain.customer.Customer
import chanwoo.cherhy.ktor.domain.customer.CustomerId
import chanwoo.cherhy.ktor.domain.customer.CustomerName

data class LoginRequest(
    val email: String,
    val password: String,
)

data class CustomerRequest(
    val name: String,
    val email: String,
    val password: String,
    val phoneNumber: String?,
)

data class CustomerResponse(
    val id: CustomerId,
    val name: CustomerName,
    val email: String,
    val password: String,
    val phoneNumber: String?,
) {
    companion object {
        fun of(
            customer: Customer,
        ) = with(customer) {
            CustomerResponse(
                id = id.value,
                name = name,
                email = email,
                password = password,
                phoneNumber = phoneNumber,
            )
        }
    }
}