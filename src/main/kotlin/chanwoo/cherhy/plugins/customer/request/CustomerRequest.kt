package chanwoo.cherhy.plugins.customer.request

data class CustomerRequest(
    val id: Long,
    val name: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
)