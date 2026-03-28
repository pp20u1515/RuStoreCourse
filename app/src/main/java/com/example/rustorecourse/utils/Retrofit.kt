package com.example.rustorecourse.utils

import com.example.rustorecourse.datasource.remote.api.INetworkApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInvoker {
    private const val BASE_URL = "http://185.103.109.134/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: INetworkApi = retrofit.create(INetworkApi::class.java)
}