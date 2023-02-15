package com.example.myredditapp.network

import retrofit2.Response

sealed class ApiResponse<T>() {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            val body = response.body()
            return if(response.isSuccessful) {
                if(body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body = body)
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                return ApiErrorResponse(errorMsg ?: "Something went wrong")
            }
        }
    }
}

class ApiSuccessResponse<T>(val body: T): ApiResponse<T>()
class ApiErrorResponse<T>(val errorMessage: String): ApiResponse<T>()
class ApiEmptyResponse<T>: ApiResponse<T>()