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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.animation.doOnEnd
import com.toy.compose_retrofit.ui.theme.Compose_retrofitTheme
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.naver.maps.map.compose.*
import com.toy.compose_retrofit.viewmodel.MapViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MapViewModel by viewModels()

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

        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        actionBar?.hide()

        setContent {
            Compose_retrofitTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TopAppBarWithTitle(viewModel)
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TopAppBarWithTitle(viewModel: MapViewModel){

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.appname),
            color = Color.White,
            fontWeight = FontWeight.Bold
            )},
            backgroundColor = colorResource(id = R.color.orange),
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = Color.White,
                    )
                }
            }
        )
    }) {
        DrawMap(viewModel)
    }
}

@SuppressLint("MissingPermission")
@Composable
fun DrawMap(viewModel: MapViewModel) {
    val rentalDataList by viewModel.rentalDataList.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.getRentalData()
    }

    Column() {
        if(rentalDataList.isNotEmpty()){
            LazyColumn() {
                items (rentalDataList) { item ->
                    item.rnAdres?.let { Text(text = it) }
                    Divider()
                }
            }
            NaverMap(
                modifier = Modifier.fillMaxSize(),
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
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    /*Compose_retrofitTheme {
        DrawMap();
    }*/
}