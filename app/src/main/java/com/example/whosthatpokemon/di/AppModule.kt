package com.example.whosthatpokemon.di

import android.content.Context
import androidx.room.Room
import com.example.whosthatpokemon.data.api.PokemonAPI
import com.example.whosthatpokemon.data.dao.ScoreDAO
import com.example.whosthatpokemon.data.repository.AppDatabase
import com.example.whosthatpokemon.data.repository.PokemonRepository
import com.example.whosthatpokemon.data.repository.PokemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL: String = "https://pokeapi.co/api/v2/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonApi(retrofit: Retrofit): PokemonAPI {
        return retrofit.create(PokemonAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "RssReader"
        ).build()
    }

    @Provides
    @Singleton
    fun provideScoreDao(appDatabase: AppDatabase): ScoreDAO {
        return appDatabase.scoreDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPokemonRepository(
        pokemonRepositoryImpl: PokemonRepositoryImpl
    ): PokemonRepository
}
