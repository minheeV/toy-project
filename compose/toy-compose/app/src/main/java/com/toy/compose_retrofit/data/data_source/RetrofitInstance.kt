package com.toy.compose_retrofit.data.data_source

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private const val BASE_URL = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade/"
    private const val BASE_URL1 = "https://data.myhome.go.kr:443/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL1)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val rentalDataService: RentalDataService by lazy {
        retrofit.create(RentalDataService::class.java)
    }
}
