package com.example.lolketingcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.auth.di.KakaoSdkInitializer
import com.example.auth.di.NaverSDKInitializer
import com.example.lolketingcompose.navigation.NavigationGraph
import com.example.lolketingcompose.ui.theme.LoLketingComposeTheme
import com.example.lolketingcompose.ui.theme.MyBlack
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var naverSdk: NaverSDKInitializer
    @Inject
    lateinit var kakaoSdk: KakaoSdkInitializer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        naverSdk.initializeNaverSDK()
        kakaoSdk.initializeKakaoSDK()

        setContent {
            LoLketingComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MyBlack
                ) {
                    val navController = rememberNavController()
                    NavigationGraph(navController = navController)
                }
            }
        }
    }
}