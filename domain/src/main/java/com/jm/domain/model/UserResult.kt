package com.jm.domain.model

sealed class UserResult {
    class Success(val data: List<User>?) : UserResult()
    object Error : UserResult()
    object InternetError: UserResult()
}