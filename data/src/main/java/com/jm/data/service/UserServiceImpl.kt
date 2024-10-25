package com.jm.data.service

import com.jm.data.dto.NetworkResponse
import com.jm.data.dto.UserResponse
import com.jm.data.utils.CheckInternetConnection
import javax.inject.Inject

class UserServiceImpl @Inject constructor(
    private val userClient: UserClient,
    private val checkInternetConnection: CheckInternetConnection
) : UserService {
    override suspend fun getUsers(page: Int, nResults: Int): NetworkResponse<UserResponse> {

        if (checkInternetConnection.isInternetAvailable()) {
            try {
                val response = userClient.getUser(page, nResults)
                if (response.isSuccessful) {
                    response.body()?.let {
                        return NetworkResponse.Success(it)
                    } ?: run {
                        return NetworkResponse.Error(ServiceError.GENERIC)
                    }

                } else {
                    return NetworkResponse.Error(ServiceError.GENERIC)
                }
            } catch (ex: Exception) {
                return NetworkResponse.Error(ServiceError.GENERIC)
            }
        } else {
            return NetworkResponse.Error(ServiceError.NO_INTERNET)
        }

    }
}