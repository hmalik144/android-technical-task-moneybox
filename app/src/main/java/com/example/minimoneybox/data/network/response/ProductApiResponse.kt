package com.example.minimoneybox.data.network.response

import com.example.minimoneybox.data.models.ProductsResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ProductApiResponse {
    @SerializedName("MoneyboxEndOfTaxYear")
    @Expose
    var moneyboxEndOfTaxYear: String? = null
    @SerializedName("TotalPlanValue")
    @Expose
    var totalPlanValue: Double? = null
    @SerializedName("TotalEarnings")
    @Expose
    var totalEarnings: Double? = null
    @SerializedName("TotalContributionsNet")
    @Expose
    var totalContributionsNet: Double? = null
    @SerializedName("TotalEarningsAsPercentage")
    @Expose
    var totalEarningsAsPercentage: Double? = null
    @SerializedName("ProductResponses")
    @Expose
    var productsRespons: List<ProductsResponse>? = null
}