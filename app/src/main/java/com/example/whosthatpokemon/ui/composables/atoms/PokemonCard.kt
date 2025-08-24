package com.example.whosthatpokemon.ui.composables.atoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.whosthatpokemon.data.model.Pokemon
import com.example.whosthatpokemon.data.model.PokemonSprites
import com.example.whosthatpokemon.R
import com.example.whosthatpokemon.ui.theme.SILHOUETTE

enum class Difficulty(val level: Int) {
    HARD(3),
    MEDIUM(2),
    EASY(1)
}
@Composable
fun PokemonCard(pokemon: Pokemon, difficulty: Difficulty, modifier: Modifier = Modifier) {
    val imageModifier = Modifier.padding(16.dp).fillMaxWidth().fillMaxHeight()
    Card(modifier = modifier.size(200.dp), colors = CardDefaults.cardColors(
        containerColor = Color.LightGray
    )) {
        if (LocalInspectionMode.current) {
            Image(
                painter = painterResource(id = R.drawable.pikachu),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = imageModifier
            )
        } else {
            AsyncImage(
                model = pokemon.sprites.default,
                contentDescription = null,
                modifier = imageModifier,
                contentScale = ContentScale.Fit,
                colorFilter = if (difficulty == Difficulty.HARD) {
                    ColorFilter.tint(
                        color = SILHOUETTE,
                        blendMode = BlendMode.SrcIn
                    )
                } else {
                    null
                }
            )
        }
    }
}

@Preview()
@Composable
fun PokemonCardPreview() {
    PokemonCard(Pokemon(name = "Pikachu",
        sprites = PokemonSprites(default = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png")
    ), difficulty = Difficulty.MEDIUM)
}