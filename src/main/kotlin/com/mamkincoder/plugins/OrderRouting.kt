package com.mamkincoder.plugins

import com.mamkincoder.data.orderStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.ordersRouting() {
    get("/order") {
        if (orderStorage.isEmpty()) {
            call.respondText("No orders found", status = HttpStatusCode.OK)
        } else {
            call.respond(orderStorage)
        }
    }
}

fun Route.getOrderRoute() {
    get("/order/{id?}") {
        val id = call.parameters["id"]
            ?: return@get call.respondText(
                "Missing ID",
                status = HttpStatusCode.BadRequest
            )
        val order = orderStorage.find { it.number == id }
            ?: return@get call.respondText(
                "No order with id $id",
                status = HttpStatusCode.NotFound
            )
        call.respond(order)
    }
}

fun Route.totalOrderPriceRoute() {
    get("/order/{id?}/total") {
        val id = call.parameters["id"]
            ?: return@get call.respondText(
                "Missing ID",
                status = HttpStatusCode.BadRequest
            )
        val order = orderStorage.find { it.number == id }
            ?: return@get call.respondText(
                "No order with id $id",
                status = HttpStatusCode.NotFound
            )
        val totalPrice = order.contents.sumOf { it.price * it.amount }
        call.respond(totalPrice)
    }
}