package com.example.tryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tryapp.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepo: UserPreferencesRepository
) : ViewModel() {

    val userName = userRepo.userName.stateIn(viewModelScope, SharingStarted.Lazily, "")
    val userAge = userRepo.userAge.stateIn(viewModelScope, SharingStarted.Lazily, 0)
    val userAuthorized =
        userRepo.userAuthorized.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun saveUser(name: String, age: Int, authorized: Boolean) {
        viewModelScope.launch {
            userRepo.saveUser(name, age, authorized)
        }
    }
}
