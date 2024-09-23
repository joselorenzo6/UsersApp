package com.jm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jm.domain.GetUsersUseCase
import com.jm.domain.model.User
import com.jm.domain.model.UserResult
import com.jm.ui.model.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewmodel @Inject constructor(private val caseUse: GetUsersUseCase) : ViewModel() {
    private val _users = MutableLiveData<UserState>()
    val users = _users

    private var nPage = 1
    private val nItems = 20
    private val userList = mutableListOf<User>()
    var selectedUser: User? = null
        private set

    init {
        onUserRequested()
    }

    fun onUserRequested() {
        _users.value = UserState.Loading()

        viewModelScope.launch {
            val result = caseUse(nPage, nItems)
            _users.value = when (result) {
                is UserResult.Success -> {
                    nPage++
                    result.data?.let {
                        userList.addAll(it)
                    }
                    UserState.Success(userList)
                }

                is UserResult.Error -> UserState.Error(result.message ?: "")
            }

        }
    }

    fun selectUser(user: User){
        selectedUser = user
    }


}