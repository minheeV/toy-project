package com.toy.compose_retrofit.data.data_source

import com.toy.compose_retrofit.data.model.RentalDTO
import retrofit2.Call
import retrofit2.http.Query

class RentalDataRepository {
    private val rentalDataService = RetrofitInstance.rentalDataService

    fun getRentalData(
        @Query("ServiceKey") serviceKey: String,
        @Query("brtcCode") brtcCode: String,
        @Query("signguCode") signguCode: String,
        @Query("numOfRows") numOfRows: String,
        @Query("pageNo") pageNo: String
    ): Call<RentalDTO> {
        return rentalDataService.getRentalData(
            serviceKey, brtcCode, signguCode, numOfRows, pageNo
        )
    }
}