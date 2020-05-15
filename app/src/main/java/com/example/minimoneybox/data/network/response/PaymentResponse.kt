package com.example.minimoneybox.data.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class PaymentResponse {
    @SerializedName("Moneybox")
    @Expose
    var moneybox: Double? = null
}