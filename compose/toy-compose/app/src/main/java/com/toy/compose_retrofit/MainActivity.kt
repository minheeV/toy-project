@file:OptIn(ExperimentalNaverMapApi::class)

package com.toy.compose_retrofit

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.toy.compose_retrofit.ui.theme.Compose_retrofitTheme
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.naver.maps.map.compose.*



class MainActivity : ComponentActivity(){
    private val permissionRequest = 99
    private var permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                    != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

}

@SuppressLint("MissingPermission")
@Composable
fun DrawMap() {
    val context = LocalContext.current as Activity

    Box(Modifier.fillMaxSize()) {
        NaverMap(
            locationSource = rememberFusedLocationSource(),
            properties = MapProperties(
                locationTrackingMode = LocationTrackingMode.Follow,
            ),
            uiSettings = MapUiSettings(
                isLocationButtonEnabled = true,
            ),
            onLocationChange = {
                //println("lon : ${it.longitude} , lat : ${it.latitude}")
            }

        ){}
    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Compose_retrofitTheme {
        DrawMap()
    }
}