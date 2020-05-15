package com.example.minimoneybox.data.network

import com.example.minimoneybox.data.network.interceptors.HeaderInterceptor
import com.example.minimoneybox.data.network.interceptors.NetworkConnectionInterceptor
import com.example.minimoneybox.data.network.response.LoginResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("users/login")
    suspend fun loginApiRequest(@Body body: HashMap<String, String>): Response<LoginResponse>

    /**
     * Return [Retrofit] class with api to login
     * [NetworkConnectionInterceptor] to intercept when there is no network
     * [HeaderInterceptor] add custom headers to retrofit calls
     */
    companion object{
        operator fun invoke(
            //injected @params
            networkConnectionInterceptor: NetworkConnectionInterceptor,
            customHeaderInterceptor: HeaderInterceptor
        ) : LoginApi{

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
                .create(LoginApi::class.java)
        }
    }

}