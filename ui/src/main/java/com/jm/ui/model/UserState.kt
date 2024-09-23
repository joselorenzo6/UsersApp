package com.jm.ui.model

import com.jm.domain.model.User

sealed class UserState {
    class Loading : UserState()
    class Success(val data: List<User>?) : UserState()
    class Error(val message: String) : UserState()
}

