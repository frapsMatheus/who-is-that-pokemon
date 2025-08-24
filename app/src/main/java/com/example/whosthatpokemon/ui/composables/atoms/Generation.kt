package com.example.whosthatpokemon.ui.composables.atoms

import android.R.attr.onClick
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.whosthatpokemon.ui.theme.CHARCOAL
import com.example.whosthatpokemon.ui.theme.GOLD
import com.example.whosthatpokemon.ui.theme.RED
import com.example.whosthatpokemon.utils.toRoman


@Composable
fun Generation(generation: Int, backgroundColor: Color, onClick: (Int) -> Unit = {}, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        modifier = modifier.size(100.dp),
        onClick = {onClick(generation)},
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.padding(16.dp).fillMaxWidth().fillMaxHeight()) {
            Text(toRoman(generation),  modifier = Modifier.padding(bottom = 8.dp), style = MaterialTheme.typography.titleLarge, color = GOLD)
            Text("Generation", style = MaterialTheme.typography.titleMedium, color = CHARCOAL)
        }
    }
}

@Preview
@Composable
fun GenerationPreview() {
    Generation(4, Color.Gray)
}