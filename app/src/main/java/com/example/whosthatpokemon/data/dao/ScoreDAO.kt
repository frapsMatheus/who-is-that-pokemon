package com.example.whosthatpokemon.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.whosthatpokemon.data.model.Score
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDAO  {
    @Upsert
    fun upsertScore(score : Score)

    @Query("SELECT * FROM scores WHERE generation = :generation")
    fun getScoreByGenerationFlow(generation : Int) : Flow<Score?>
}