package com.toy.compose_retrofit.data

import com.naver.maps.geometry.LatLng
import com.toy.compose_retrofit.retrofit.data.RentalData


data class RentalItem(
    //매매 정보
    val rentalItem: RentalData?,
    //지오코딩
    val rentalGeo:LatLng?
)