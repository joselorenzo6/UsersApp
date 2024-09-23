package com.jm.domain

import com.jm.domain.model.UserResult
import com.jm.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(page: Int, nResults: Int): UserResult{
        return userRepository.getUsers(page, nResults)
    }
}