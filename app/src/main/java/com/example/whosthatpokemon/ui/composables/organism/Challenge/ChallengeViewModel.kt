package com.example.whosthatpokemon.ui.composables.organism.Challenge

import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whosthatpokemon.data.model.Generation
import com.example.whosthatpokemon.data.model.Pokemon
import com.example.whosthatpokemon.data.repository.PokemonRepository
import com.example.whosthatpokemon.ui.composables.atoms.Difficulty
import com.example.whosthatpokemon.ui.navigation.Challenge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val generationId: Int = checkNotNull(savedStateHandle[Challenge.generationArg])

    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()
    private val _currentPokemon = MutableStateFlow<Pokemon?>(null)
    val currentPokemon = _currentPokemon.asStateFlow()
    private val _currentDifficulty = MutableStateFlow<Difficulty>(Difficulty.HARD)
    val currentDifficulty = _currentDifficulty.asStateFlow()
    val _totalPokemons = MutableStateFlow(0)
    val totalPokemons = _totalPokemons.asStateFlow()

    val _totalCorrect = MutableStateFlow(0)
    val totalCorrect = _totalCorrect.asStateFlow()

    private val _inputText = MutableStateFlow("")
    val inputText = _inputText.asStateFlow()
    private var currentGeneration: Generation? = null

    init {
        getNextChallenge()
    }

    fun updateInputText(newText: String) {
        viewModelScope.launch {
            _inputText.emit(newText)
        }
    }

    fun changeDifficulty() {
        viewModelScope.launch {
            if (_currentDifficulty.value === Difficulty.EASY) {
                _totalPokemons.value++
                getNextChallenge()
            }
            _currentDifficulty.emit(when (_currentDifficulty.value) {
                Difficulty.HARD -> Difficulty.MEDIUM
                Difficulty.MEDIUM -> Difficulty.EASY
                Difficulty.EASY -> Difficulty.HARD
            })
        }
    }

    fun checkAnswer() {
        if (_inputText.value.equals(_currentPokemon.value!!.name, ignoreCase = true)) {
            _totalCorrect.value++
            _totalPokemons.value++
            getNextChallenge()
        } else {
            changeDifficulty()
        }
    }

    fun getNextChallenge() {
        viewModelScope.launch {
            _loading.emit(true)
            if (currentGeneration == null) {
                val response = pokemonRepository.getGenerationById(generationId)
                if (response.isSuccessful) {
                    currentGeneration = response.body()
                } else {
//                    TODO: Add error handling
                    getNextChallenge()
                }
            }
            if (currentGeneration != null) {
                val nextPokemon = currentGeneration!!.species.random()
                val response = pokemonRepository.getPokemonByName(nextPokemon.name)
                if (response.isSuccessful) {
                    _inputText.emit("")
                    _currentPokemon.emit(response.body())
                    _currentDifficulty.emit(Difficulty.HARD)
                    _loading.emit(false)
                } else {
//                    TODO: Add error handling
                    getNextChallenge()
                }
            }
        }
    }

}