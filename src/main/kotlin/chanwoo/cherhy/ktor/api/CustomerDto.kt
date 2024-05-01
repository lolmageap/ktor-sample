package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.domain.customer.Customer

data class LoginRequest(
    val email: String,
    val password: String,
)

data class CustomerRequest(
    val id: Long,
    val name: String,
    val email: String,
    val password: String,
    val phoneNumber: String?,
)

data class CustomerResponse(
    val id: Long,
    val name: String,
    val email: String,
    val password: String,
    val phoneNumber: String?,
) {
    companion object {
        fun of(customer : Customer) =
            with(customer) {
                CustomerResponse(
                    id = id.value,
                    name = name,
                    email = email,
                    password = password,
                    phoneNumber = phoneNumber
                )
            }
    }
}