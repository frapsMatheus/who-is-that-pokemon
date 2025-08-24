package com.example.whosthatpokemon.data.api

import com.example.whosthatpokemon.data.model.Generation
import com.example.whosthatpokemon.data.model.Pokemon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPI {
    @GET("generation/{id}")
    suspend fun getGenerationById(@Path("id") id: Int): Response<Generation>

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name") name: String): Response<Pokemon>
}