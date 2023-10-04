package com.toy.compose_retrofit.data.data_source

import com.toy.compose_retrofit.data.model.RentalDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RentalDataService {
    @GET("rentalHouseList")
    fun getRentalData(
        @Query("ServiceKey") serviceKey: String,
        @Query("brtcCode") brtcCode: String,
        @Query("signguCode") signguCode: String,
        @Query("numOfRows") numOfRows: String,
        @Query("pageNo") pageNo: String
    ): Call<RentalDTO>
}
