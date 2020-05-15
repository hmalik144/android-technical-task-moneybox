package com.example.minimoneybox.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ProductsResponse {
    @SerializedName("Id")
    @Expose
    var id: Int? = null
    @SerializedName("PlanValue")
    @Expose
    var planValue: Double? = null
    @SerializedName("Moneybox")
    @Expose
    var moneybox: Double? = null
    @SerializedName("SubscriptionAmount")
    @Expose
    var subscriptionAmount: Double? = null
    @SerializedName("TotalFees")
    @Expose
    var totalFees: Double? = null
    @SerializedName("IsSelected")
    @Expose
    var isSelected: Boolean? = null
    @SerializedName("IsFavourite")
    @Expose
    var isFavourite: Boolean? = null
    @SerializedName("CollectionDayMessage")
    @Expose
    var collectionDayMessage: String? = null
    @SerializedName("Product")
    @Expose
    var product: Product? = null
    @SerializedName("state")
    @Expose
    var state: Int? = null
}