package chanwoo.cherhy.plugins.api

import chanwoo.cherhy.plugins.domain.customer.Customer

data class CustomerRequest(
    val id: Long,
    val name: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
)

data class CustomerResponse(
    val id: Long,
    val name: String,
    val email: String,
) {
    companion object {
        fun of(customer : Customer) =
            with(customer) {
                CustomerResponse(
                    id = id.value,
                    name = name,
                    email = email,
                )
            }
    }
}