package com.toy.compose_retrofit.retrofit

import com.toy.compose_retrofit.retrofit.data.RentalDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {
    @GET("rentalHouseList")
    fun getRentalList(
        @Query("ServiceKey") serviceKey:String,
        @Query("brtcCode") brtcCode:String,
        @Query("signguCode") signguCode:String,
        @Query("numOfRows") numOfRows:String,
        @Query("pageNo") pageNo:String
    ): Call<RentalDTO>
}