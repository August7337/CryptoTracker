package com.example.cryptotracker.view

import android.icu.text.NumberFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.util.Locale
import androidx.compose.ui.unit.dp
import com.example.cryptotracker.models.Crypto
import com.example.cryptotracker.ui.theme.Purple40
import com.example.cryptotracker.viewModel.CryptoViewModel

@Composable
fun CryptoListScreen(viewModel: CryptoViewModel = CryptoViewModel()) {
    val cryptos by viewModel.cryptos

    if (cryptos.isEmpty()) {
        CircularProgressIndicator()
    } else {
        LazyColumn (
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(cryptos) {
                CryptoCard(it)
            }
        }
    }
}

@Composable
fun CryptoCard(crypto: Crypto) {
    //Price
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
    val formattedPrice = currencyFormatter.format(crypto.priceUsd)

    //MC
    val formattedMarketCap = format(crypto.marketCapUsd)

    //Change
    val color24h = if (crypto.percentChange24h >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
    val color7d = if (crypto.percentChange7d >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Purple40)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "${crypto.name} (${crypto.symbol})",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Text(
                text = formattedPrice,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "Market Cap: $formattedMarketCap",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.LightGray
            )

            Text(
                text = "24h Change: ${crypto.percentChange24h}%",
                style = MaterialTheme.typography.bodyMedium,
                color = color24h
            )
            Text(
                text = "7d Change: ${crypto.percentChange7d}%",
                style = MaterialTheme.typography.bodyMedium,
                color = color7d
            )
        }
    }
}

fun format(value: Double): String {
    return when {
        value >= 1_000_000_000 -> String.format(Locale.US, "$%.2f B", value / 1_000_000_000)
        value >= 1_000_000 -> String.format(Locale.US, "$%.2f M", value / 1_000_000)
        else -> String.format(Locale.US, "$%,.0f", value)
    }
}