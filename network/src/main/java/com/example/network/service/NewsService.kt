package com.example.network.service

import com.example.network.BuildConfig
import com.example.network.model.NewsResult
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsService {

    @Headers(value = [
        "X-Naver-Client-Id: ${BuildConfig.CLIENT_ID}",
        "X-Naver-Client-Secret: ${BuildConfig.CLIENT_SECRET}"
    ])
    @GET("search/news.json")
    suspend fun fetchNews(
        @Query("query") query : String, // 검색을 원하는 문자열로서 UTF-8로 인코딩한다.
        @Query("display") display : Int, // 검색 결과 출력 건수 지정, 기본값 10 최대값 100
        @Query("start") start : Int, // 검색 시작 위치 기본값 1, 최대값 1000까지 가능
        @Query("sort") sort : String // 정렬 옵션: sim (유사도순), date (날짜순)
    ) : NewsResult

}