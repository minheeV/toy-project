package com.toy.compose_retrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.NaverMapSdk.NaverCloudPlatformClient
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import com.toy.compose_retrofit.ui.theme.Compose_retrofitTheme


@ExperimentalNaverMapApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //AndroidManifest에 CLIENT_ID 등록했지만 지도가 나타나지 않아 우선 API로 CLIENT_ID 등록
        NaverMapSdk.getInstance(this).client =
            NaverCloudPlatformClient(BuildConfig.NAVER_CLIENT_ID)

        setContent {
            Compose_retrofitTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }

    }
}

@ExperimentalNaverMapApi
@Composable
fun Greeting(name: String) {
    NaverMap(
        modifier = Modifier.fillMaxSize()
    )
}

@ExperimentalNaverMapApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Compose_retrofitTheme {
        Greeting("Android")
    }
}