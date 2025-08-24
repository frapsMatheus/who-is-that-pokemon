package com.example.whosthatpokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.whosthatpokemon.ui.navigation.MainNavigation
import com.example.whosthatpokemon.ui.theme.WhosThatPokemonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhosThatPokemonTheme {
                MainNavigation()
            }
        }
    }
}