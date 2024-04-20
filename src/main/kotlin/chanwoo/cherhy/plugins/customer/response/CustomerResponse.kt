package chanwoo.cherhy.plugins.customer.response

import chanwoo.cherhy.plugins.customer.entity.Customer

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