package com.jm.data.service

import com.jm.data.dto.NetworkResponse
import com.jm.data.dto.UserResponse

interface UserService {
    suspend fun getUsers(page: Int, nResults: Int ): NetworkResponse<UserResponse>

}