package com.example.lolketingcompose

import android.app.Application
import com.example.network.NetworkManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LoLKetingCompose : Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkManager().initFirebase(applicationContext)
    }
}