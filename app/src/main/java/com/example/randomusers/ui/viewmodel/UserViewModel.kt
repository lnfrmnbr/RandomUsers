package com.example.randomusers.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomusers.data.model.User
import com.example.randomusers.data.remote.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    var users by mutableStateOf<List<User>>(emptyList())
        private set
    var errorMessage by mutableStateOf<String?>(null)

    init {
        loadUsers()
    }

    fun loadUsers(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                users = repository.getUsers(forceRefresh)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "Ошибка загрузки данных"
            }
        }
    }
}
