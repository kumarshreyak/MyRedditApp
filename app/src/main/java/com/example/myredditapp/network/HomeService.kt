package com.example.myredditapp.network

import android.app.Application
import com.example.myredditapp.network.models.GetAccessTokenRequest
import com.example.myredditapp.network.models.GetAccessTokenResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton


interface HomeService {

    @POST("/access_token")
    suspend fun getAccessToken(@Body request: GetAccessTokenRequest): GetAccessTokenResponse
}

@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {
    val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
    val baseClient = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

    const val HOME_BASE_URL = "https://www.reddit.com/api/v1/"

    @Provides
    @Singleton
    fun provideHomeService(): HomeService {
        return Retrofit.Builder()
            .baseUrl(HOME_BASE_URL)
            .client(baseClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HomeService::class.java)
    }
}