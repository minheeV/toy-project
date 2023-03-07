package com.toy.compose_retrofit.retrofit.data

import com.google.gson.annotations.SerializedName

data class RentalDTO(
    //코드
    @SerializedName("code") var code:String,
    @SerializedName("hsmpList") val list:List<RentalData>,
    //메세지
    @SerializedName("msg") val msg:String
)
