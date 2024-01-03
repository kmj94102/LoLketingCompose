package com.example.network.client

import com.example.network.service.MainService
import javax.inject.Inject

class MainClient @Inject constructor(
    private val service: MainService
) {
}