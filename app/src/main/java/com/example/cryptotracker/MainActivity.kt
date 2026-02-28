package com.example.cryptotracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptotracker.ui.theme.CryptoTrackerTheme
import com.example.cryptotracker.view.CryptoListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(horizontal = 24.dp, vertical = 16.dp)
                    ) {
                        Text(
                            text = "Crypto Tracker",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.SansSerif,
                            letterSpacing = (-1.5).sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 32.dp)
                        )

                        CryptoListScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CryptoTrackerTheme {
        Greeting("Android")
    }
}