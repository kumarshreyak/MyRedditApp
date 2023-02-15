package com.example.myredditapp.network.models

class GetAccessTokenRequest(
    val grant_type: String,
    val device_id: String,
)


class GetAccessTokenResponse(
    val access_token: String,
)