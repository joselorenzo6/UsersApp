package com.jm.data.repository

import com.jm.data.dto.toDomain
import com.jm.data.service.UserService
import com.jm.domain.model.UserResult
import com.jm.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userService: UserService): UserRepository {
    override suspend fun getUsers(page: Int, nResults: Int) : UserResult {
        val result = userService.getUsers(page,nResults)
        return result.toDomain()
    }
}