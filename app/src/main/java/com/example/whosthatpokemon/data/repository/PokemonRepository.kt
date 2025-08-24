package com.example.whosthatpokemon.data.repository

import com.example.whosthatpokemon.data.model.Generation
import com.example.whosthatpokemon.data.model.Pokemon
import retrofit2.Response

interface PokemonRepository {
    suspend fun getGenerationById(id: Int): Response<Generation>
    suspend fun getPokemonByName(name: String): Response<Pokemon>
}