package com.toy.compose_retrofit.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toy.compose_retrofit.BuildConfig
import com.toy.compose_retrofit.data.data_source.RentalDataRepository
import com.toy.compose_retrofit.data.model.RentalDTO
import com.toy.compose_retrofit.data.model.RentalData
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
    private val TAG = MapViewModel::class.java.simpleName

    private val repository = RentalDataRepository()

    private val _rentalDataList = MutableLiveData<List<RentalData>>()
    val rentalDataList: LiveData<List<RentalData>> = _rentalDataList

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }

    private fun onError(message: String) {
        Log.d(TAG, message)
    }

    fun getRentalData() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            try {
                val response = repository.getRentalData(
                    BuildConfig.api_key,
                    "11",
                    "110",
                    "10",
                    "1"
                )
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful)
                        _rentalDataList.value = response.body()?.list
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }


        fun resultGeocoding(list: List<RentalData>) {
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
                            setRequestProperty(
                                "X-NCP-APIGW-API-KEY-ID",
                                BuildConfig.naver_client_id
                            )
                            setRequestProperty(
                                "X-NCP-APIGW-API-KEY",
                                BuildConfig.naver_client_secret
                            )
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
                        //Log.d("tag", "x: $x, y: $y")


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            }
        }
    }
}