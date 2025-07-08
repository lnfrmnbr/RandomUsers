package com.example.randomusers.ui.viewmodel

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomusers.data.model.User
import com.example.randomusers.data.remote.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun loadUser(userId: String) {
        viewModelScope.launch {
            try {
                _user.value = repository.getUser(userId)
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Ошибка загрузки данных"
            }
        }
    }

    fun sendEmail(context: Context, email: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:".toUri()
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        }
        val chooser = Intent.createChooser(intent, "Отправить сообщение через...")
        context.startActivity(chooser)
    }

    fun makePhoneCall(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = "tel:$phoneNumber".toUri()
        }
        val chooser = Intent.createChooser(intent, "Позвонить с помощью...")
        context.startActivity(chooser)
    }

    fun openMap(context: Context, address: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = "geo:0,0?q=$address".toUri()
        }
        val chooser = Intent.createChooser(intent, "Посмотреть на карте с помощью...")
        context.startActivity(chooser)
    }
}