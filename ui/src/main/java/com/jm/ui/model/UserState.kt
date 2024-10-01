package com.jm.ui.model

import com.jm.domain.model.User

sealed class UserState {
    data object Loading : UserState()
    data class Success(val data: List<User>?) : UserState()
    data class Error(val message: String) : UserState()
}

