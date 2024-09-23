package com.jm.domain.model

sealed class UserResult(val data: List<User>?, val message: String?) {
    class Success(data: List<User>?) : UserResult(data, null)
    class Error(message: String) : UserResult(null, message)
}