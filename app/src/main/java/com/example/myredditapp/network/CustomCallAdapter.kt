package com.example.myredditapp.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class ApiResponseCallAdapter<Input>(private val responseType: Type): CallAdapter<Input, Flow<ApiResponse<Input>>> {
    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<Input>): Flow<ApiResponse<Input>> {
        return object : Flow<ApiResponse<Input>> {
            private var started = AtomicBoolean(false)

            override suspend fun collect(collector: FlowCollector<ApiResponse<Input>>) {
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<Input> {
                        override fun onResponse(call: Call<Input>, response: Response<Input>) {
                            flow<ApiResponse<Input>> { emit(ApiResponse.create(response)) }
                        }

                        override fun onFailure(call: Call<Input>, throwable: Throwable) {
                            flow<ApiResponse<Input>> { emit(ApiResponse.create(throwable)) }
                        }
                    })
                }
            }
        }
    }
}



class ApiResponseCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Flow::class.java) {
            return null
        }
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        if (rawObservableType != ApiResponse::class.java) {
            throw IllegalArgumentException("type must be a resource")
        }
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        val bodyType = getParameterUpperBound(0, observableType)
        return ApiResponseCallAdapter<Any>(bodyType)
    }
}

