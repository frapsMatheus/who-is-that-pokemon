package com.example.whosthatpokemon.data.model

import com.google.gson.annotations.SerializedName

data class PokemonSprites(
    @SerializedName("front_default")
    val default: String
)

data class Pokemon(
    val name: String,
    val sprites: PokemonSprites
)