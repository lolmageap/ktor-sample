package chanwoo.cherhy.plugins.api

import chanwoo.cherhy.plugins.util.EndPoint.CUSTOMER.CREATE_CUSTOMER
import chanwoo.cherhy.plugins.util.EndPoint.CUSTOMER.GET_CUSTOMER
import chanwoo.cherhy.plugins.util.EndPoint.CUSTOMER.GET_CUSTOMERS
import chanwoo.cherhy.plugins.domain.customer.CustomerService
import chanwoo.cherhy.plugins.util.EndPoint.CUSTOMER.DELETE_CUSTOMER
import chanwoo.cherhy.plugins.util.EndPoint.CUSTOMER.UPDATE_CUSTOMER
import chanwoo.cherhy.plugins.util.PageRequest
import chanwoo.cherhy.plugins.util.id
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.customer() {
    val customerService: CustomerService by inject()
    
    get(GET_CUSTOMERS) {
        val requestCondition = call.receive<PageRequest>()
        customerService.getAll(requestCondition)
    }

    get(GET_CUSTOMER) {
        val customerId = call.id
        val customer = customerService.get(customerId)
        call.respond(customer)
    }

    post(CREATE_CUSTOMER) {
        val request = call.receive<CustomerRequest>()
        val customer = customerService.save(request)
        call.respond(customer)
    }

    put(UPDATE_CUSTOMER) {
        val customerId = call.id
        val request = call.receive<CustomerRequest>()
        customerService.update(customerId, request)
        call.respond("Customer updated")
    }

    delete(DELETE_CUSTOMER) {
        val customerId = call.id
        customerService.delete(customerId)
        call.respond("Customer deleted")
    }
}