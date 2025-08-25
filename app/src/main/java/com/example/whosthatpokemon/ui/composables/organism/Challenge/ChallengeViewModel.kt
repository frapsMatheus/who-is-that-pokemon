package com.example.whosthatpokemon.ui.composables.organism.Challenge

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whosthatpokemon.data.dao.ScoreDAO
import com.example.whosthatpokemon.data.model.Generation
import com.example.whosthatpokemon.data.model.Pokemon
import com.example.whosthatpokemon.data.model.Score
import com.example.whosthatpokemon.data.repository.PokemonRepository
import com.example.whosthatpokemon.ui.composables.atoms.Difficulty
import com.example.whosthatpokemon.ui.navigation.Challenge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val scoreDAO: ScoreDAO,
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val generationId: Int = checkNotNull(savedStateHandle[Challenge.generationArg])

    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()
    private val _currentPokemon = MutableStateFlow<Pokemon?>(null)
    val currentPokemon = _currentPokemon.asStateFlow()
    private val _currentDifficulty = MutableStateFlow(Difficulty.HARD)
    val currentDifficulty = _currentDifficulty.asStateFlow()

    private val _inputText = MutableStateFlow("")
    val inputText = _inputText.asStateFlow()

    private var currentGeneration: Generation? = null
    private val _score = scoreDAO.getScoreByGenerationFlow(generationId)

    private val _totalPokemons = MutableStateFlow(0)
    val totalPokemons = _totalPokemons.asStateFlow()
    private val _totalCorrect = MutableStateFlow(0)
    val totalCorrect = _totalCorrect.asStateFlow()
    var score = Score(0)

    init {
        viewModelScope.launch {
            getNextChallenge()
            _score.collect { it ->
                if (it != null) {
                    score = it
                    _totalPokemons.value = it.pokemons
                    _totalCorrect.value = it.scoreHard + it.scoreMedium + it.scoreEasy
                }
            }
        }
    }

    fun updateInputText(newText: String) {
        viewModelScope.launch {
            _inputText.emit(newText)
        }
    }

    fun updateScore(difficulty: Difficulty, success: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val newScore = Score(generationId,
                    score.pokemons + 1,
                    scoreHard = score.scoreHard + (if (difficulty == Difficulty.HARD && success) 1 else 0),
                    scoreMedium = score.scoreMedium + (if (difficulty == Difficulty.MEDIUM && success) 1 else 0),
                    scoreEasy = score.scoreEasy + (if (difficulty == Difficulty.EASY && success) 1 else 0)
                )
                scoreDAO.upsertScore(newScore)
            }
        }
    }

    fun changeDifficulty() {
        viewModelScope.launch {
            if (_currentDifficulty.value === Difficulty.EASY) {
                updateScore(_currentDifficulty.value, false)
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
            updateScore(_currentDifficulty.value, true)
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