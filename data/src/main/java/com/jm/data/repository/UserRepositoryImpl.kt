package com.jm.data.repository

import com.jm.data.dto.toDomain
import com.jm.data.service.UserService
import com.jm.domain.model.UserResult
import com.jm.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userService: UserService) :
    UserRepository {
    override suspend fun getUsers(page: Int, nResults: Int): Flow<UserResult> {
        return flow {
            val result = userService.getUsers(page, nResults)
            emit(result.toDomain())
        }.catch {
            emit(UserResult.Error(it.message?:""))
        }
        .flowOn(Dispatchers.IO)
    }
}