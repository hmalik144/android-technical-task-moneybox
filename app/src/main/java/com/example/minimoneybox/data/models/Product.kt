package com.example.minimoneybox.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Product {
    @SerializedName("Id")
    @Expose
    var id: Int? = null
    @SerializedName("Name")
    @Expose
    var name: String? = null
    @SerializedName("CategoryType")
    @Expose
    var categoryType: String? = null
    @SerializedName("Type")
    @Expose
    var type: String? = null
    @SerializedName("FriendlyName")
    @Expose
    var friendlyName: String? = null
    @SerializedName("CanWithdraw")
    @Expose
    var canWithdraw: Boolean? = null
    @SerializedName("ProductHexCode")
    @Expose
    var productHexCode: String? = null
    @SerializedName("AnnualLimit")
    @Expose
    var annualLimit: Double? = null
    @SerializedName("DepositLimit")
    @Expose
    var depositLimit: Double? = null
    @SerializedName("MinimumWeeklyDeposit")
    @Expose
    var minimumWeeklyDeposit: Double? = null
    @SerializedName("MaximumWeeklyDeposit")
    @Expose
    var maximumWeeklyDeposit: Double? = null
}