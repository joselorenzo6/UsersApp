package com.jm.data.service

import com.jm.data.dto.NetworkResponse
import com.jm.data.dto.UserResponse
import javax.inject.Inject

class UserServiceImpl @Inject constructor(private val userClient: UserClient) : UserService {
    override suspend fun getUsers(page: Int, nResults: Int): NetworkResponse<UserResponse> {
        try {
            val response = userClient.getUser(page, nResults)
            if (response.isSuccessful) {
                response.body()?.let {
                    return NetworkResponse.Success(it)
                } ?: run {
                    return NetworkResponse.Error("")
                }

            } else {
                return NetworkResponse.Error(response.message() ?: "")
            }
        } catch (ex: Exception) {
            return NetworkResponse.Error(ex.message ?: "error")
        }
    }
}