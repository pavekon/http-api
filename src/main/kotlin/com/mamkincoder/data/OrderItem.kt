package com.mamkincoder.data

import kotlinx.serialization.Serializable

@Serializable
data class OrderItem(val item: String, val amount: Int, val price: Double)