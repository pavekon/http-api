package com.mamkincoder.plugins

import com.mamkincoder.data.Customer
import com.mamkincoder.data.customerStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {
    route("/customer") {
        get {
            if (customerStorage.isEmpty()) {
                call.respondText("No customers found", status = HttpStatusCode.OK)
            } else {
                call.respond(customerStorage)
            }
        }
        get("{id?}") {
            val id = call.parameters["id"]
                ?: return@get call.respondText(
                    "Missing ID",
                    status = HttpStatusCode.BadRequest
                )
            val customer = customerStorage.find { it.id == id }
                ?: return@get call.respondText(
                    "No customer with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(customer)
        }
        post {
            val customer = call.receive<Customer>()
            customerStorage.add(customer)
            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: call.respond(HttpStatusCode.BadRequest)
            if (customerStorage.removeIf { it.id == id }) {
                call.respondText("Customer removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("No customer with id $id", status = HttpStatusCode.NotFound)
            }
        }
    }
}