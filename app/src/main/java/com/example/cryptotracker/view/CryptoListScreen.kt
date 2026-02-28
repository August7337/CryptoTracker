package com.example.cryptotracker.view

import android.icu.text.NumberFormat
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import java.util.Locale
import androidx.compose.ui.unit.dp
import com.example.cryptotracker.models.Crypto
import com.example.cryptotracker.viewModel.CryptoViewModel

@Composable
fun CryptoListScreen(viewModel: CryptoViewModel = CryptoViewModel()) {
    val cryptos by viewModel.cryptos
    val favoriteIds by viewModel.favoriteIds
    val favs by viewModel.favs


    if (cryptos.isEmpty()) {
        CircularProgressIndicator()
    } else {
        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            if (favs.isNotEmpty()) {
                item {
                    Text(
                        text = "Favorites",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                }
                items(favs) { crypto->

                    val isFav = favoriteIds.contains(crypto.id)
                    CryptoCard(
                        crypto,
                        isFavorite = isFav,
                        onFavoriteClick = {viewModel.toggleFavorite(crypto) }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(64.dp))
                }

            }

            item {
                Text(
                    text = "Trending cryptocurrencies",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                )
            }

            items(cryptos) { crypto->

                val isFav = favoriteIds.contains(crypto.id)
                CryptoCard(
                    crypto,
                    isFavorite = isFav,
                    onFavoriteClick = {viewModel.toggleFavorite(crypto) }
                )
            }
        }
    }
}
@Composable
fun CryptoCard(
    crypto: Crypto,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {}
    ) {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
    val formattedPrice = currencyFormatter.format(crypto.priceUsd)

    val formattedMarketCap = format(crypto.marketCapUsd)

    val color24h = if (crypto.percentChange24h >= 0) Color(0xFF4CAF50) else Color(0xFFFF5252) // Un rouge plus "néon"
    val color7d = if (crypto.percentChange7d >= 0) Color(0xFF4CAF50) else Color(0xFFFF5252)
    val sign24h = if (crypto.percentChange24h > 0) "+" else ""
    val sign7d = if (crypto.percentChange7d > 0) "+" else ""

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.15f),
                shape = RoundedCornerShape(20.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.05f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        IconButton(
            onClick = onFavoriteClick,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favori",
                tint = if (isFavorite) Color(0xFFFF5252) else Color.Gray
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = crypto.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = crypto.symbol,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "MCap: $formattedMarketCap",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = formattedPrice,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$sign24h${crypto.percentChange24h}% (24h)",
                    style = MaterialTheme.typography.bodySmall,
                    color = color24h,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "$sign7d${crypto.percentChange7d}% (7d)",
                    style = MaterialTheme.typography.bodySmall,
                    color = color7d,
                    fontWeight = FontWeight.SemiBold
                )
            }
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