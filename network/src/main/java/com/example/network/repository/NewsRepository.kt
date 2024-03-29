package com.example.network.repository

import com.example.network.model.NewsResult
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun fetchNews(
        display: Int,
        start: Int,
    ): Flow<NewsResult>
}