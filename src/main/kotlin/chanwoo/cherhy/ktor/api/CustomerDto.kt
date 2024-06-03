package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.domain.customer.Customer
import chanwoo.cherhy.ktor.domain.customer.CustomerId
import chanwoo.cherhy.ktor.domain.customer.CustomerName

data class LoginRequest(
    val email: CustomerName,
    val password: String,
)

data class CustomerRequest(
    val name: String,
    val email: CustomerName,
    val password: String,
    val phoneNumber: String?,
)

data class CustomerResponse(
    val id: CustomerId,
    val name: String,
    val email: CustomerName,
    val password: String,
    val phoneNumber: String?,
) {
    companion object {
        @JvmStatic
        fun of(
            customer: Customer,
        ) = with(customer) {
            CustomerResponse(
                id = CustomerId.of(id.value),
                name = name,
                email = CustomerName.of(email),
                password = password,
                phoneNumber = phoneNumber,
            )
        }
    }
}