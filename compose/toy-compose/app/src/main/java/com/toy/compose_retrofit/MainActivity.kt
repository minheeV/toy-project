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
import com.naver.maps.map.compose.*
import com.toy.compose_retrofit.retrofit.RetrofitAPI
import com.toy.compose_retrofit.retrofit.data.RentalDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
fun GetJsonData(rentalList: MutableList<RentalDTO>, ctx: Context){

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
    ).enqueue(object : Callback<RentalDTO>{
        override fun onResponse(call: Call<RentalDTO>, response: Response<RentalDTO>) {
            if(response.isSuccessful.not()) return
            rentalList.add(0, response.body()!!)
        }
        override fun onFailure(call: Call<RentalDTO>, t: Throwable) {
            t.printStackTrace()
        }
    })
}

@SuppressLint("MissingPermission")
@Composable
fun DrawMap() {
    val context = LocalContext.current as Activity
    val data = remember { mutableStateListOf<RentalDTO>()}
    GetJsonData(rentalList = data, ctx = context)
    LazyColumn(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        items(data){item ->
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
            ){
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