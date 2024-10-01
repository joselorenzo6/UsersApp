package com.jm.domain.repository

import com.jm.domain.model.UserResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUsers(page: Int, nResults: Int): Flow<UserResult>
}