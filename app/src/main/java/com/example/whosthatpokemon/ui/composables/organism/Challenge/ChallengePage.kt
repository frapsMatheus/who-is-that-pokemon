package com.example.whosthatpokemon.ui.composables.organism.Challenge

import com.example.whosthatpokemon.R
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.whosthatpokemon.ui.composables.atoms.Difficulty
import com.example.whosthatpokemon.ui.composables.atoms.PokemonCard
import com.example.whosthatpokemon.ui.theme.CHARCOAL
import com.example.whosthatpokemon.ui.theme.GOLD
import com.example.whosthatpokemon.ui.theme.RED

@Composable
fun ChallengePage(viewModel: ChallengeViewModel, modifier: Modifier = Modifier) {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val loading = viewModel.loading.collectAsState().value
    val currentPokemon = viewModel.currentPokemon.collectAsState().value
    val totalPokemon = viewModel.totalPokemons.collectAsState().value
    val totalCorrect = viewModel.totalCorrect.collectAsState().value
    val firstLetter = currentPokemon?.name?.first()?.uppercaseChar()
    val lastLetter = currentPokemon?.name?.last()?.uppercaseChar()
    val currentDifficulty = viewModel.currentDifficulty.collectAsState().value
    val inputText = viewModel.inputText.collectAsState()
    Scaffold(topBar = {
        Row(modifier = Modifier.background(RED).fillMaxWidth().padding(top = 48.dp).padding(horizontal = 16.dp)) {
            Image(painter = painterResource(R.drawable.close),
                contentDescription = "Close",
                modifier = Modifier.size(30.dp).clickable(true, onClick = {
                    onBackPressedDispatcher?.onBackPressed()
                }),
                colorFilter = ColorFilter.tint(
                    color = GOLD,
                    blendMode = BlendMode.SrcIn
                )
            )
        }
    }) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(RED),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            if (loading || currentPokemon == null) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp),
                    color = GOLD,
                    trackColor = GOLD.copy(alpha = 0.2f)
                )
            } else {
                if (totalPokemon > 0) {
                    Text("Score", color = GOLD, style = MaterialTheme.typography.titleLarge)
                    Text("$totalCorrect/$totalPokemon", color = GOLD, style = MaterialTheme.typography.titleLarge )
                }
                Column (modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    PokemonCard(currentPokemon, currentDifficulty)
                    Text(text = "Who's that Pokémon?",
                        color = GOLD,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = modifier.padding(top = 16.dp))
                    if (currentDifficulty != Difficulty.HARD) {
                        Text(if (currentDifficulty == Difficulty.MEDIUM) {
                            "Try again!"
                        } else {
                            "Last chance! Name start with $firstLetter and ends with $lastLetter"
                        })
                    }
                }
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GOLD,
                        focusedLabelColor = GOLD,
                        focusedTextColor = GOLD,
                        unfocusedBorderColor = CHARCOAL,
                        unfocusedLabelColor = GOLD
                    ),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = inputText.value,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel.checkAnswer()
                        }
                    ),
                    onValueChange = { it -> viewModel.updateInputText(it) },
                    label = { Text("Answer") }
                )
                Button (
                    modifier = modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CHARCOAL
                    ),
                    onClick = { viewModel.checkAnswer() }
                ) {
                    Text("SUBMIT ANSWER", style = MaterialTheme.typography.titleMedium, color = GOLD)
                }
            }
        }
    }
}