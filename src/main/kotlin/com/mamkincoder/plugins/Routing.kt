package com.mamkincoder.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        customerRouting()
        ordersRouting()
        getOrderRoute()
        totalOrderPriceRoute()
    }
}
