package com.example.cryptotracker.models

import com.google.gson.annotations.SerializedName

data class CoinloreResponse(
    @SerializedName("data") val data: List<Crypto>
)
