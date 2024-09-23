package com.jm.data.service

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class UserInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d("URL requested ", request.url().toString())
        val response = chain.proceed(request)
        return response
    }
}