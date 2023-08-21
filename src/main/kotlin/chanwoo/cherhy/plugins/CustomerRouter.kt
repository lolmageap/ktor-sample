package chanwoo.cherhy.plugins

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
                    val customerId = call.parameters["id"]

                    if (customerId != null) {
                        val customer = fetchCustomerById(customerId)
                        if (customer != null) {
                            call.respond(customer)
                        } else {
                            call.respondText("Customer not found", status = HttpStatusCode.NotFound)
                        }
                    } else {
                        call.respondText("No customer ID provided", status = HttpStatusCode.BadRequest)
                    }
                }

                post {

                }
                delete("{id?}") {

                }
            }
        }

        fun fetchCustomerById(id: String): Customer? {
            return null
        }

    }

}

data class Customer(val id: String, val name: String, val email: String)
