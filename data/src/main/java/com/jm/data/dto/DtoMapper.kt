package com.jm.data.dto

import com.jm.data.service.ServiceError
import com.jm.domain.model.User
import com.jm.domain.model.UserResult

fun NetworkResponse<UserResponse>.toDomain(): UserResult {
    return when (this) {
        is NetworkResponse.Success -> UserResult.Success(this.data?.results?.map {
            User(
                "${it.name.first} ${it.name.last} ",
                it.dob.age.toString(),
                "${it.location.street.name}, ${it.location.street.number}",
                it.email,
                it.location.country,
                it.gender,
                it.picture.large
            )
        })

        is NetworkResponse.Error -> {
            if (this.error?.name == ServiceError.NO_INTERNET.name) {
                UserResult.InternetError
            } else {
                UserResult.Error
            }

        }
    }
}