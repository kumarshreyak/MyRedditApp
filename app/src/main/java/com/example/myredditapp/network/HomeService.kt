package com.example.myredditapp.network

import com.example.myredditapp.network.models.GetAccessTokenRequest
import com.example.myredditapp.network.models.GetAccessTokenResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import okhttp3.Credentials
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton


interface HomeService {

    @retrofit2.http.Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("/api/v1/access_token")
    fun getAccessToken(@Body request: GetAccessTokenRequest): Flow<ApiResponse<GetAccessTokenResponse>>
}

@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {
    val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    val baseClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val req = chain.request()
            val newHeaders = Headers.Builder().addAll(req.headers)
            newHeaders.add("Authorization", Credentials.basic(CLIENT_ID, ""))
            val newReq = req.newBuilder().headers(newHeaders.build()).build()
            chain.proceed(newReq)
        }
        .addInterceptor(logger)
        .build()

    const val HOME_BASE_URL = "https://www.reddit.com/"
    private const val CLIENT_ID = ""

    @Provides
    @Singleton
    fun provideHomeService(): HomeService {
        return Retrofit.Builder()
            .baseUrl(HOME_BASE_URL)
            .client(baseClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .build()
            .create(HomeService::class.java)
    }
}