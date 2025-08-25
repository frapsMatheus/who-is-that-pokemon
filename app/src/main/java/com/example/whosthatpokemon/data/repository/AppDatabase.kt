package com.example.whosthatpokemon.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.whosthatpokemon.data.dao.ScoreDAO
import com.example.whosthatpokemon.data.model.Score

@Database(entities = [Score::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDAO
}