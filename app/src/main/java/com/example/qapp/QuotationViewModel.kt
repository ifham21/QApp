package com.example.qapp

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class QuotationViewModel : ViewModel() {
    var selectedOffice = mutableStateOf("Auckland Offices")
    var selectedTab = mutableStateOf("General")
    var itemName = mutableStateOf("")
    var reason = mutableStateOf("")
    var price = mutableStateOf("")
    var quantity = mutableStateOf("")
    var discount = mutableStateOf("")
    var items = mutableStateListOf<Item>()

    val totalAmount: String
        get() = String.format("%.2f", items.sumOf { it.total })

    fun addItem() {
        if (itemName.value.isNotEmpty() && price.value.isNotEmpty() && quantity.value.isNotEmpty()) {
            items.add(Item(
                name = itemName.value,
                price = price.value.toDouble(),
                quantity = quantity.value.toInt(),
                discount = discount.value.toDoubleOrNull() ?: 0.0
            ))
            itemName.value = ""
            reason.value = ""
            price.value = ""
            quantity.value = ""
            discount.value = ""
        }
    }
}
