package com.example.minimoneybox.data.repository

import com.example.minimoneybox.data.network.interceptors.HeaderInterceptor
import com.example.minimoneybox.data.network.interceptors.NetworkConnectionInterceptor
import com.example.minimoneybox.data.network.response.LoginResponse
import com.example.minimoneybox.data.network.response.PaymentResponse
import com.example.minimoneybox.data.network.response.ProductApiResponse
import retrofit2.Retrofit

/**
 * Repository interface to implement repository methods
 *
 */
interface MoneyBoxRepository {

    suspend fun loginUser(email: String, password: String): LoginResponse?

    fun saveCredentials(email: String, password: String)

    fun loadCredentials(): Pair<String, String>

    fun saveAuthToken(token: String)

    fun loadAuthToken(): String?

    suspend fun getProducts(authCode: String): ProductApiResponse?

    suspend fun oneOffPayment(produceId: Int, authCode: String): PaymentResponse?
}