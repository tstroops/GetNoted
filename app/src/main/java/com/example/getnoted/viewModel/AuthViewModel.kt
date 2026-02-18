package com.example.getnoted.viewmodel
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getnoted.data.AuthRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


// Initializes the UI state
data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val passwordConfirm: String = "",
    val authState: AuthState = AuthState.Loading,
    val errorMessage: String? = null
)


// For handling if the user is logged in or not
enum class AuthState {
    IsAuthorized, // Is logged in
    NotAuthorized, // Is logged out
    Loading // Fetching Session Status
}

class AuthViewModel(): ViewModel() {
    private val TAG = "AuthViewModel"

    // Expose UI state
    // The private editable uistate only changeable by viewmodel
    private val _uiState = MutableStateFlow(AuthUiState())
    // The public read only ui state that the UI can read to display
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            // If the session returns a token then the user is logged in
            if (AuthRepository.getCurrentSession() != null)
            {
                _uiState.update { currentState ->
                    currentState.copy(authState = AuthState.IsAuthorized)

                }
            }
            else
            {
                // If the session is doesn't return a token then the user is not logged in
                _uiState.update { currentState ->
                    currentState.copy(authState = AuthState.NotAuthorized)
                }
            }
        }
    }

    // Call this helper function whenever we navigate to either sign up or sign in functions
    // This is to keep the fields from overlapping between screens
    fun resetFields() {
        _uiState.update { currentState ->
            currentState.copy(
            email = "",
            password = "",
            passwordConfirm = "",
            errorMessage = null
            )
        }
    }

    // Function to change the email to display to the UI
    fun emailChanged(newEmail: String) {
        _uiState.update { currentState ->
            currentState.copy(email = newEmail)
        }
    }

    // Function to change password to display to the UI
    fun passwordChanged(newPassword: String) {
        _uiState.update { currentState ->
            currentState.copy(password = newPassword)
        }
    }

    // Function to change password to display to the UI
    fun passwordConfirmChanged(newPassword: String) {
        _uiState.update { currentState->
            currentState.copy(passwordConfirm = newPassword)
        }
    }

    fun signUp() {
        // Grab current UI fields
        val email = _uiState.value.email
        val password = _uiState.value.password

        viewModelScope.launch {
            try {
                AuthRepository.signUp(email, password)

                // If successful, update state
                _uiState.update { currentState ->
                    currentState.copy(authState = AuthState.IsAuthorized, errorMessage = null)
                }
            } catch (e: Exception) {
                // If the signup wasn't successful
                _uiState.update { currentState ->
                    currentState.copy(errorMessage = "Sign Up Failed")
                }
            }
        }
    }


    fun signIn() {
        // Grab current UI fields
        val email = _uiState.value.email
        val password = _uiState.value.password
        Log.d(TAG, "Signing up with email")

        viewModelScope.launch {
            try {
                AuthRepository.signIn(email, password)
                Log.d(TAG, "Sign Ip Success :)")

                // If successful, update status
                _uiState.update { currentState ->
                    currentState.copy(authState = AuthState.IsAuthorized)
                }
            }
            catch (e: Exception) {
                Log.d(TAG,"Sign In Failed :(")

                // If not successful, show error message
                _uiState.update { currentState ->
                    currentState.copy(errorMessage = "Sign in Failed")
                }
            }
        }
    }
}