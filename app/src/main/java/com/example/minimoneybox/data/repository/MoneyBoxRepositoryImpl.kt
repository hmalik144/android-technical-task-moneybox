package com.example.minimoneybox.data.repository

import com.example.minimoneybox.data.network.LoginApi
import com.example.minimoneybox.data.network.UserAccountApi
import com.example.minimoneybox.data.network.response.LoginResponse
import com.example.minimoneybox.data.network.response.PaymentResponse
import com.example.minimoneybox.data.network.response.ProductApiResponse
import com.example.minimoneybox.data.prefs.KeystoreStorage
import com.example.minimoneybox.data.network.SafeApiCall
import com.example.minimoneybox.data.network.interceptors.HeaderInterceptor
import com.example.minimoneybox.data.network.interceptors.NetworkConnectionInterceptor
import com.google.gson.Gson
import retrofit2.Retrofit

/**
 * [MoneyBoxRepository] implementations in this Repository
 *
 */
open class MoneyBoxRepositoryImpl(
    private val prefs: KeystoreStorage,
    private val loginApi: LoginApi,
    private val userAccountApi: UserAccountApi,
    private val gson: Gson
) : MoneyBoxRepository, SafeApiCall() {

    override suspend fun loginUser(
        email: String,
        password: String
    ): LoginResponse{
        val hash = HashMap<String, String>()
        hash["Email"] = email
        hash["Password"] = password
        hash["Idfa"] = "ANYTHING"

        return responseUnwrap{
            loginApi.loginApiRequest(hash)
        }
    }

    override fun saveCredentials(email: String, password: String) {
        prefs.saveCredentialsInPrefs(email, password)
    }

    override fun loadCredentials(): Pair<String, String> {
        return prefs.loadCredentialsFromPrefs()
    }

    override fun saveAuthToken(token: String) {
        prefs.saveTokenInPrefs(token)
    }

    override fun loadAuthToken(): String? {
        return prefs.loadTokenFromPrefs()
    }

    override suspend fun getProducts(authCode: String): ProductApiResponse {
        return responseUnwrap{
            userAccountApi.getProductsFromApi("Bearer $authCode")
        }
    }

    override suspend fun oneOffPayment(
        produceId: Int, authCode: String
    ): PaymentResponse? {
        val hash = HashMap<String, Int>()
        hash["Amount"] = 20
        hash["InvestorProductId"] = produceId

        return responseUnwrap {
            userAccountApi.oneOffPaymentsFromApi(hash,"Bearer $authCode")
        }
    }
}