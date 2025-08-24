package com.example.whosthatpokemon.ui.composables.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.whosthatpokemon.ui.composables.atoms.Generation

@Composable
fun GenerationsGrid(onClick: (Int) -> Unit = {}, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier.padding(horizontal = 16.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(9) { index ->
            Generation(index + 1, onClick = onClick, backgroundColor = Color.White)
        }

    }
}

@Preview
@Composable
fun GenerationsGridPreview() {
    GenerationsGrid()
}