package com.example.myredditapp.ui.theme.home

import com.example.myredditapp.network.HomeService
import com.example.myredditapp.network.ApiResponse
import com.example.myredditapp.network.handleApi
import com.example.myredditapp.network.models.GetAccessTokenRequest
import com.example.myredditapp.network.models.GetAccessTokenResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import java.util.*
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeService: HomeService,
) {
    suspend fun getAccessToken(
        request: GetAccessTokenRequest = GetAccessTokenRequest(
            grant_type = "https://oauth.reddit.com/grants/installed_client",
            device_id = "DO_NOT_TRACK_THIS_DEVICE",
        )
    ): Flow<ApiResponse<GetAccessTokenResponse>> {
        return flow {
            emit(handleApi { homeService.getAccessToken(grant_type = request.grant_type, device_id = request.device_id) })
        }
    }
}