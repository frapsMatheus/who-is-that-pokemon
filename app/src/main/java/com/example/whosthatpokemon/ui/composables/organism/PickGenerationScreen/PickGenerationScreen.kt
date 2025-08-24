package com.example.whosthatpokemon.ui.composables.organism.PickGenerationScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.whosthatpokemon.ui.composables.molecules.GenerationsGrid
import com.example.whosthatpokemon.ui.theme.GOLD
import com.example.whosthatpokemon.ui.theme.RED

@Composable
fun PickGenerationScreen(navigateToChallenge: (generation: Int) -> Unit = {}, modifier: Modifier = Modifier) {
    Scaffold(
        modifier,
        topBar = {
        Row (modifier = Modifier.padding(48.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
        Text("Pick a Generation", style = MaterialTheme.typography.titleLarge, color = GOLD)
    }}, containerColor = RED) { innerPadding ->
        GenerationsGrid(modifier = Modifier.padding(innerPadding), onClick = navigateToChallenge)
    }
}

@Preview
@Composable
private fun PickGenerationScreenPreview() {
    PickGenerationScreen()
}