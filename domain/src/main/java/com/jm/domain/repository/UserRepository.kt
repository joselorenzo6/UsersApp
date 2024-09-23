package com.jm.domain.repository

import com.jm.domain.model.UserResult

interface UserRepository {

    suspend fun getUsers(page: Int, nResults: Int): UserResult
}