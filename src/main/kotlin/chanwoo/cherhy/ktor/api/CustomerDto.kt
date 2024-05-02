package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.domain.customer.Customer
import org.jetbrains.exposed.dao.id.EntityID

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
    val id: EntityID<Long>,
    val name: String,
    val email: String,
    val password: String,
    val phoneNumber: String?,
) {
    companion object {
        fun of(customer : Customer) =
            with(customer) {
                CustomerResponse(
                    id = id,
                    name = name,
                    email = email,
                    password = password,
                    phoneNumber = phoneNumber
                )
            }
    }
}