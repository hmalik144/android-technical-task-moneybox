package com.example.minimoneybox.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class HeaderInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val request: Request = original.newBuilder()
            .header("AppId", "3a97b932a9d449c981b595")
            .header("Content-Type", "application/json")
            .header("appVersion","7.8.0")
            .header("apiVersion", "3.0.0")
            .method(original.method(), original.body())
            .build()

        return chain.proceed(request)
    }
}