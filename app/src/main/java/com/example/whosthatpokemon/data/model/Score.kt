package com.example.whosthatpokemon.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class Score (
    @PrimaryKey val generation: Int,
    val pokemons: Int = 0,
    @ColumnInfo(name = "score_hard") val scoreHard: Int = 0,
    @ColumnInfo(name = "score_medium") val scoreMedium: Int = 0,
    @ColumnInfo(name = "score_easy") val scoreEasy: Int = 0
)