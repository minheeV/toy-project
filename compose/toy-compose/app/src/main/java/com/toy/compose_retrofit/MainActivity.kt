@file:OptIn(ExperimentalNaverMapApi::class)

package com.toy.compose_retrofit

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.toy.compose_retrofit.ui.theme.Compose_retrofitTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.*
import com.naver.maps.map.util.FusedLocationSource
import com.toy.compose_retrofit.MainActivity.Companion.LOCATION_PERMISSION_REQUEST_CODE


class MainActivity : ComponentActivity() {

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

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

@Composable
fun DrawMap() {
    val context = LocalContext.current as Activity
    val locationSources = FusedLocationSource(context, LOCATION_PERMISSION_REQUEST_CODE)
    val testPos = LatLng(37.584697, 126.885875)

    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                maxZoom = 15.0,
                minZoom = 8.0,
                locationTrackingMode = LocationTrackingMode.Follow
            )
        )
    }

    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(isLocationButtonEnabled = true)
        )
    }

    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition(testPos, 13.0)
    }

    Box(Modifier.fillMaxSize()) {
        NaverMap(
            locationSource = locationSources,
            properties = mapProperties,
            uiSettings = mapUiSettings,
            cameraPositionState = cameraPositionState,
            onLocationChange = {
                println("lon : ${it.longitude} , lat : ${it.latitude}")
            }

        ){
            Marker(
                state = MarkerState(position = testPos),
                captionText = "Marker",
                captionColor = Color.Blue
            )
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