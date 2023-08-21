package chanwoo.cherhy.plugins.customer.service

import chanwoo.cherhy.plugins.customer.request.CustomerRequest

object CustomerService {

    fun getCustomerById(id: String?): CustomerRequest {
        return CustomerRequest(id = id ?: "김찬우", name = "kim", email = "chan@woo.com")
    }

}