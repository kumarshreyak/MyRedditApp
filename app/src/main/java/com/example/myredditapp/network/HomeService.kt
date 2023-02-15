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
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.inject.Singleton


interface HomeService {

    @FormUrlEncoded
    @POST("/api/v1/access_token")
    suspend fun getAccessToken(@Field("grant_type") grant_type: String, @Field("device_id") device_id: String): Response<GetAccessTokenResponse>
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
            newHeaders.add("User-Agent"," android:com.example.myshrekapp:v1.0.0 (by /u/unbatedfall)")
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
            .build()
            .create(HomeService::class.java)
    }
}