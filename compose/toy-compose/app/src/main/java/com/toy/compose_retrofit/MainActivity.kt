@file:OptIn(ExperimentalNaverMapApi::class)

package com.toy.compose_retrofit

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.animation.doOnEnd
import com.toy.compose_retrofit.ui.theme.Compose_retrofitTheme
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.*
import com.toy.compose_retrofit.retrofit.data.RentalDTO
import com.toy.compose_retrofit.viewmodel.MapViewModel


class MainActivity : ComponentActivity() {
    private val mapViewModel by viewModels<MapViewModel>()

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
                Surface(modifier = Modifier.fillMaxSize()) {
                    DrawMap(rentalList = mapViewModel.rentalResponse)
                    mapViewModel.getRentalList()
                }
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

@SuppressLint("MissingPermission")
@Composable
fun DrawMap(
    rentalList: List<RentalDTO>,
    viewModel: MapViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(rentalList) { item ->
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
                viewModel.resultGeocoding(item.list.get(0).rnAdres.toString())
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
/*    Compose_retrofitTheme {
        DrawMap()
    }*/
}