package com.example.getnoted.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getnoted.data.AuthSupaBase
import kotlinx.coroutines.launch

class SignUpViewModel: ViewModel() {
    var password by mutableStateOf("")
    var passwordConfirm by mutableStateOf("")
    var email by mutableStateOf("")

    fun updateEmail(newEmail: String) {
        email = newEmail
    }

    fun updatePassword(newPassword: String) {
        password = newPassword
    }

    fun updatePasswordConfirm(newPasswordConfirm: String) {
        passwordConfirm = newPasswordConfirm
    }

    fun signUp() {
        viewModelScope.launch {
            AuthSupaBase.signUp(email, password)
        }
    }

    companion object
}