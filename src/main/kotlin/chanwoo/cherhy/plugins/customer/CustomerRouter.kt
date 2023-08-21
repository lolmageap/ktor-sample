package chanwoo.cherhy.plugins.customer

import chanwoo.cherhy.plugins.customer.request.CustomerRequest
import chanwoo.cherhy.plugins.customer.service.CustomerService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class CustomerRouter {

    companion object{
        fun Route.customerRouting() {
            route("/customer") {
                get {
                    call.respondText("No customers found", status = HttpStatusCode.OK)
                }

                get("{id?}") {
                    val customerId : String? = call.parameters["id"]
                    val customer : CustomerRequest = CustomerService.getCustomerById(customerId)

                    call.respond(customer)
                    call.respondText("Customer not found", status = HttpStatusCode.NotFound)
                }

                post {

                }

                delete("{id?}") {

                }

            }
        }
    }

}


