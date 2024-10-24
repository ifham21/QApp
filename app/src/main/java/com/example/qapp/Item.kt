package com.example.qapp

data class Item(
    val name: String,
    val price: Double,
    val quantity: Int,
    val discount: Double
) {
    val total: Double
        get() = price * quantity * (1 - discount / 100)
}
