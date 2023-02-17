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
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?,
    ): Response<PokemonList>

}

@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {

    const val HOME_BASE_URL = "https://www.reddit.com/"
    private const val CLIENT_ID = ""

    @Provides
    @Singleton
    fun provideHomeService(@ApplicationContext appContext: Context): HomeService {
        val baseClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val req = chain.request()
                val newHeaders = Headers.Builder().addAll(req.headers)
                runBlocking {
                    val token = appContext.dataStore.readData(ACCESS_TOKEN)
                    if (token.isNullOrBlank()) {
                        newHeaders.add("Authorization", Credentials.basic(CLIENT_ID, ""))
                    } else {
                        newHeaders.add("Authorization", Credentials.basic(token, ""))
                    }
                }
                newHeaders.add(
                    "User-Agent",
                    " android:com.example.myshrekapp:v1.0.0 (by /u/unbatedfall)"
                )
                val newReq = req.newBuilder().headers(newHeaders.build()).build()
                chain.proceed(newReq)
            }
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