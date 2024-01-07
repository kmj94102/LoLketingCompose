package com.example.network.repository

import com.example.network.model.SearchResult
import kotlinx.coroutines.flow.Flow

interface AddressRepository {

    /** 주소 조회 **/
    fun fetchAddress(keyword : String, currentPage: Int): Flow<SearchResult>

}