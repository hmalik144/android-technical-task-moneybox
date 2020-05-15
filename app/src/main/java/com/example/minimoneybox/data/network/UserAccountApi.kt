package com.example.minimoneybox.data.network

import com.example.minimoneybox.data.network.interceptors.HeaderInterceptor
import com.example.minimoneybox.data.network.interceptors.NetworkConnectionInterceptor
import com.example.minimoneybox.data.network.response.PaymentResponse
import com.example.minimoneybox.data.network.response.ProductApiResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserAccountApi {

    @GET("investorproducts")
    suspend fun getProductsFromApi(
        @Header("Authorization") authHeader: String
    ): Response<ProductApiResponse>

    @POST("oneoffpayments")
    suspend fun oneOffPaymentsFromApi(
        @Body body: HashMap<String, Int>,
        @Header("Authorization") authHeader: String
    ): Response<PaymentResponse>

    /**
     * Return [Retrofit] class with api to User accounts
     * [NetworkConnectionInterceptor] to intercept when there is no network
     * [HeaderInterceptor] add custom headers to retrofit calls
     */
    companion object {
        operator fun invoke(
            //injected @params
            networkConnectionInterceptor: NetworkConnectionInterceptor,
            customHeaderInterceptor: HeaderInterceptor
        ): UserAccountApi {

            //okHttpClient with interceptors
            val okkHttpclient = OkHttpClient.Builder()
                .addNetworkInterceptor(networkConnectionInterceptor)
                .addInterceptor(customHeaderInterceptor)
                .build()

            //retrofit to be used in @Repository
            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://api-test01.moneyboxapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserAccountApi::class.java)
        }
    }

}