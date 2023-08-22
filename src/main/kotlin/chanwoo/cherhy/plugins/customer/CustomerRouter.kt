package chanwoo.cherhy.plugins.customer

import chanwoo.cherhy.plugins.customer.request.CustomerRequest
import chanwoo.cherhy.plugins.customer.service.CustomerService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.lang.RuntimeException

object CustomerRouter {

    fun Route.customerRouting() {

        route("/customer") {
            get {
                call.respondText("No customers found", status = HttpStatusCode.OK)
            }

            get("{id?}") {
                val customerId: Long = call.parameters["id"]?.toLongOrNull()
                    ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid data passed")

                val customer : CustomerRequest = CustomerService.getCustomerById(customerId)

                call.respond(customer)
//                call.respondText("Customer not found", status = HttpStatusCode.NotFound)
            }

            post {

            }

            delete("{id?}") {

            }

        }
    }

}


