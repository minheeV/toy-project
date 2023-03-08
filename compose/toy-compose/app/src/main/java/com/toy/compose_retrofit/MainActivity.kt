@file:OptIn(ExperimentalNaverMapApi::class)

package com.toy.compose_retrofit

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.animation.doOnEnd
import com.toy.compose_retrofit.ui.theme.Compose_retrofitTheme
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.gson.Gson
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.*
import com.toy.compose_retrofit.retrofit.RetrofitAPI
import com.toy.compose_retrofit.retrofit.data.RentalDTO
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread


class MainActivity : ComponentActivity() {
    private val permissionRequest = 99
    private var permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                ObjectAnimator.ofFloat(splashScreenView, View.ALPHA, 1f, 0f).run {
                    interpolator = AnticipateInterpolator()
                    duration = 400L
                    doOnEnd { splashScreenView.remove() }
                    start()
                }
            }
        }
        if (!isPermitted())
            ActivityCompat.requestPermissions(this, permissions, permissionRequest)

        setContent {
            Compose_retrofitTheme {
                DrawMap()
            }
        }

    }

    /**
     * 위치 권한
     */
    private fun isPermitted(): Boolean {
        for (perm in permissions) {
            if (ContextCompat.checkSelfPermission(this, perm)
                != PackageManager.PERMISSION_GRANTED
            ) return false
        }
        return true
    }
}

/**
 * retrofit
 */
@Composable
fun GetJsonData(rentalList: MutableList<RentalDTO>, ctx: Context) {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://data.myhome.go.kr:443/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val rentalService = retrofit.create(RetrofitAPI::class.java)

    rentalService.getRentalList(
        BuildConfig.api_key,
        "11",
        "140",
        "10",
        "1"
    ).enqueue(object : Callback<RentalDTO> {
        override fun onResponse(call: Call<RentalDTO>, response: Response<RentalDTO>) {
            if (response.isSuccessful.not()) return
            rentalList.add(0, response.body()!!)
        }

        override fun onFailure(call: Call<RentalDTO>, t: Throwable) {
            t.printStackTrace()
        }
    })
}

@Composable
fun RequestGeocode(addr: String) {
    thread(start = true) {
        try {
            val query =
                "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + URLEncoder.encode(
                    addr,
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
            val x = a?.getJSONObject(0)?.getString("x")
            val y = a?.getJSONObject(0)?.getString("y")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}

@SuppressLint("MissingPermission")
@Composable
fun DrawMap() {
    val context = LocalContext.current as Activity
    val data = remember { mutableStateListOf<RentalDTO>() }
    GetJsonData(rentalList = data, ctx = context)
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(data) { item ->
            NaverMap(
                modifier = Modifier.fillParentMaxHeight(),
                locationSource = rememberFusedLocationSource(),
                properties = MapProperties(
                    locationTrackingMode = LocationTrackingMode.Follow,
                ),
                uiSettings = MapUiSettings(
                    isLocationButtonEnabled = true,
                ),
                onLocationChange = {
                }
            ) {
                Marker(
                    state = MarkerState(position = LatLng(37.532600, 127.024612)),
                    captionText = item.list.get(0).rnAdres
                )
                RequestGeocode(item.list.get(0).rnAdres!!)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Compose_retrofitTheme {
        DrawMap()
    }
}