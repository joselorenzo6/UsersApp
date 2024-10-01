package com.jm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jm.domain.GetUsersUseCase
import com.jm.domain.model.User
import com.jm.domain.model.UserResult
import com.jm.ui.model.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewmodel @Inject constructor(private val caseUse: GetUsersUseCase) : ViewModel() {


    private val _users = MutableStateFlow<UserState>(UserState.Loading)
    val users: StateFlow<UserState> = _users

    private var nPage = 1
    private val nItems = 20
    private val userList = mutableListOf<User>()
    var selectedUser: User? = null
        private set

    init {
        onUserRequested()
    }

    fun onUserRequested() {
        viewModelScope.launch {
            _users.emit(UserState.Loading)
            caseUse(nPage, nItems).collect {
                when (it) {

                    is UserResult.Error -> {
                        _users.emit(UserState.Error(it.message ?: ""))
                    }
                    is UserResult.Success -> {
                        nPage++
                        it.data?.let { retrievedUsers ->
                            userList.addAll(retrievedUsers)
                            _users.emit(UserState.Success(userList))
                        }
                    }
                }
            }

        }
    }

    fun selectUser(user: User) {
        selectedUser = user
    }


}