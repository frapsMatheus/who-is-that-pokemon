package com.example.whosthatpokemon.data.model

import com.google.gson.annotations.SerializedName

data class PokemonSpecies(
    val name: String
)

data class Generation(
    @SerializedName("pokemon_species")
    val species: List<PokemonSpecies>
)