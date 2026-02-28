package com.example.cryptotracker.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cryptotracker.models.Crypto
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.cryptotracker.api.RetrofitInstance
import kotlinx.coroutines.launch


class CryptoViewModel: ViewModel() {
    private val _cryptos = mutableStateOf<List<Crypto>>(emptyList())
    val cryptos: State<List<Crypto>> = _cryptos

    private val _favs = mutableStateOf<List<Crypto>>(emptyList())
    val favs: State<List<Crypto>> = _favs

    var favoriteIds = mutableStateOf<Set<String>>(emptySet())


    init {
        fetchCryptos()
    }

    private fun fetchCryptos() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCryptos()
                _cryptos.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("CryptoViewModel", "Error: ${e.message}")
            }
        }
    }

    fun toggleFavorite(crypto: Crypto) {
        val currentFavorites = favoriteIds.value.toMutableSet()
        val currentFavs = _favs.value.toMutableList()

        if (currentFavorites.contains(crypto.id)) {
            currentFavorites.remove(crypto.id)
            currentFavs.removeAll { it.id == crypto.id }
        } else {
            currentFavorites.add(crypto.id)
            currentFavs.add(crypto)
        }

        favoriteIds.value = currentFavorites.toSet()
        _favs.value = currentFavs
    }

}