package com.toy.compose_retrofit.retrofit

import com.toy.compose_retrofit.retrofit.data.RentalDTO
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {
    @GET("rentalHouseList")
    fun getRentalList(
        @Query("ServiceKey") serviceKey: String,
        @Query("brtcCode") brtcCode: String,
        @Query("signguCode") signguCode: String,
        @Query("numOfRows") numOfRows: String,
        @Query("pageNo") pageNo: String
    ): Call<RentalDTO>

    companion object {
        var apiService: RetrofitAPI? = null
        fun getInstance(): RetrofitAPI {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://data.myhome.go.kr:443/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RetrofitAPI::class.java)
            }
            return apiService!!
        }
    }
}