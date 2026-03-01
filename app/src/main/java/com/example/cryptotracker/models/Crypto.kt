package com.example.cryptotracker.models

import android.R
import com.google.gson.annotations.SerializedName

data class Crypto(
    val id: String,
    val symbol: String,
    val name: String,
    @SerializedName("price_usd")
    val priceUsd: Double,

    @SerializedName("percent_change_24h")
    val percentChange24h: Double,

    @SerializedName("percent_change_7d")
    val percentChange7d: Double,

    @SerializedName("market_cap_usd")
    val marketCapUsd: Double,

    val tsupply: Double,
    val volume24: Double
)
