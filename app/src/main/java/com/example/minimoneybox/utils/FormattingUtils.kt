package com.example.minimoneybox.utils

import java.text.NumberFormat
import java.util.*

fun Double.toCurrency(): String{
    NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 2
        currency = Currency.getInstance("GBP")
        return format(this@toCurrency)
    }
}