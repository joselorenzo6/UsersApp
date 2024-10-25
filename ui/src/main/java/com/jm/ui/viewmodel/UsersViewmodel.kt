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


    private val _state = MutableStateFlow<UserState>(UserState.Loading)
    val state: StateFlow<UserState> = _state



    private var nPage = 1
    private val nItems = 20
    private var userList = mutableListOf<User>()
    var selectedUser: User? = null
        private set

    init {
        onUserRequested()
    }

    fun onUserRequested() {
        viewModelScope.launch {
            _state.emit(UserState.Loading)
            caseUse(nPage, nItems).collect {
                when (it) {
                    is UserResult.Error -> {
                        _state.emit(UserState.Error)
                    }
                    is UserResult.InternetError -> {
                        _state.emit(UserState.InternetError)
                    }
                    is UserResult.Success -> {
                        nPage++
                        it.data?.let { retrievedUsers ->
                            val uniqueUsers = retrievedUsers.filterNot { newUser ->
                                userList.any { it.email == newUser.email }
                            }
                            userList.addAll(uniqueUsers)
                            _state.emit(UserState.Success(userList))
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