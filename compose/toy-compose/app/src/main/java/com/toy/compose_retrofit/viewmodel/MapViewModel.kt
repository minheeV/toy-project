package com.toy.compose_retrofit.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.NaverMap
import com.toy.compose_retrofit.BuildConfig
import com.toy.compose_retrofit.data.RentalItem
import com.toy.compose_retrofit.retrofit.RetrofitAPI
import com.toy.compose_retrofit.retrofit.data.RentalDTO
import com.toy.compose_retrofit.retrofit.data.RentalData
import kotlinx.coroutines.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread

class MapViewModel : ViewModel() {
    var rentalResponse: List<RentalData> by mutableStateOf(listOf())
    var rentalList : MutableLiveData<MutableList<RentalItem>> = MutableLiveData()//mutableListOf<RentalItem>()

    fun getRentalList() {
        viewModelScope.launch {
            val apiService = RetrofitAPI.getInstance()
            val rentalList = mutableListOf<RentalDTO>()
            try {
                apiService.getRentalList(
                    BuildConfig.api_key,
                    "11",
                    "110",
                    "10",
                    "1"
                ).enqueue(object : Callback<RentalDTO> {
                    override fun onResponse(call: Call<RentalDTO>, response: Response<RentalDTO>) {
                        if (response.isSuccessful.not()) return
                        rentalList.add(0, response.body()!!)

                        rentalResponse = rentalList[0].list
                        resultGeocoding(rentalResponse)
                    }

                    override fun onFailure(call: Call<RentalDTO>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun resultGeocoding(list: List<RentalData>) {
        var listrental = mutableListOf<RentalItem>()
        thread {
            for (item in list) {
                try {
                    val query =
                        "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + URLEncoder.encode(
                            item.rnAdres,
                            "UTF-8"
                        )
                    val url = URL(query)
                    val conn = url.openConnection() as HttpURLConnection

                    val bufferedReader: BufferedReader
                    conn.apply {
                        connectTimeout = 5000
                        readTimeout = 5000
                        requestMethod = "GET"
                        setRequestProperty("X-NCP-APIGW-API-KEY-ID", BuildConfig.naver_client_id)
                        setRequestProperty("X-NCP-APIGW-API-KEY", BuildConfig.naver_client_secret)
                        doInput = true
                    }
                    val responseCode = conn.responseCode
                    bufferedReader =
                        if (responseCode == 200) BufferedReader(InputStreamReader(conn.inputStream))
                        else BufferedReader(InputStreamReader(conn.errorStream))

                    val line = bufferedReader.readLine()
                    val jsonInfo = JSONObject(line)
                    val a = jsonInfo.optJSONArray("addresses")
                    val x = a?.getJSONObject(0)?.getString("x")?.toDouble()
                    val y = a?.getJSONObject(0)?.getString("y")?.toDouble()
                    Log.d("tag", "x: $x, y: $y")


                    listrental.add(RentalItem(item, LatLng(x!!,y!!)))

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }

        rentalList.value = listrental

    }
}