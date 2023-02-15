package com.example.myredditapp.ui.theme.home

import com.example.myredditapp.network.HomeService
import com.example.myredditapp.network.ApiResponse
import com.example.myredditapp.network.models.GetAccessTokenRequest
import com.example.myredditapp.network.models.GetAccessTokenResponse
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeService: HomeService,
) {
    suspend fun getAccessToken(
        request: GetAccessTokenRequest = GetAccessTokenRequest(
            grant_type = "https://oauth.reddit.com/grants/installed_client",
            device_id = UUID.randomUUID().toString()
        )
    ): Flow<ApiResponse<GetAccessTokenResponse>> {
        return homeService.getAccessToken(request = request)
    }
}