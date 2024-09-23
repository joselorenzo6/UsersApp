package com.jm.data.service

import com.jm.data.dto.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserClient {

    @GET("api/")
   suspend fun getUser(
        @Query("page") page: Int,
        @Query("results") nResults: Int
    ):Response<UserResponse>
}

