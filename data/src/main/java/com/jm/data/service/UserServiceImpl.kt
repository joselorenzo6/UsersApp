package com.jm.data.service

import com.jm.data.dto.NetworkResponse
import com.jm.data.dto.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserServiceImpl @Inject constructor(private val userClient: UserClient) : UserService {
    override suspend fun getUsers(page: Int, nResults: Int): NetworkResponse<UserResponse> {
        try {
            return withContext(Dispatchers.IO) {
                val response = userClient.getUser(page, nResults)
                if (response.isSuccessful) {
                    response.body()?.let {
                        NetworkResponse.Success(it)
                    } ?: run {
                        NetworkResponse.Error("")
                    }

                } else {
                    NetworkResponse.Error(response.message() ?: "")
                }
            }
        } catch (ex: Exception) {
            return NetworkResponse.Error(ex.message ?: "error")
        }
    }
}