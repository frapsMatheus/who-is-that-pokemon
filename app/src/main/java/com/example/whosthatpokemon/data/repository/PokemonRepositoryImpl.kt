package com.example.whosthatpokemon.data.repository

import com.example.whosthatpokemon.data.api.PokemonAPI
import com.example.whosthatpokemon.data.model.Generation
import com.example.whosthatpokemon.data.model.Pokemon
import retrofit2.Response
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonAPI: PokemonAPI
) : PokemonRepository {

    override suspend fun getGenerationById(id: Int): Response<Generation> {
        return pokemonAPI.getGenerationById(id)
    }

    override suspend fun getPokemonByName(name: String): Response<Pokemon> {
        return pokemonAPI.getPokemonByName(name)
    }

    // Implement other methods from the interface
}