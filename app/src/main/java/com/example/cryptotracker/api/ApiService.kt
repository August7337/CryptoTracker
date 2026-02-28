package com.example.cryptotracker.api

import com.example.cryptotracker.models.CoinloreResponse
import retrofit2.http.GET

interface ApiService {
    @GET("tickers")
    suspend fun getCryptos(): CoinloreResponse
}