package com.example.myredditapp.network

import android.content.Context
import com.example.myredditapp.network.models.PokemonList
import com.example.myredditapp.util.ACCESS_TOKEN
import com.example.myredditapp.util.dataStore
import com.example.myredditapp.util.readData
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Credentials
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import javax.inject.Singleton


interface HomeService {
    @GET("api/v2/pokemon/")
    suspend fun getPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Response<PokemonList>

}

@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {

    const val HOME_BASE_URL = "https://pokeapi.co/"

    @Provides
    @Singleton
    fun provideHomeService(@ApplicationContext appContext: Context): HomeService {
        val baseClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
        return Retrofit.Builder()
            .baseUrl(HOME_BASE_URL)
            .client(baseClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(HomeService::class.java)
    }
}