package com.example.jobportal.auth

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class RegisterViewModel(application: Application) : AndroidViewModel(application){

    private val repository = UserRepository()

    var registerState = mutableStateOf(false)

    var message=mutableStateOf<String>("")

    private val sessionManager = SessionManager(application)

    var loginState = mutableStateOf<String?>(null)

    fun login(email: String, password: String) {
        viewModelScope.launch {

            val result = repository.loginUser(
                LoginRequest(email, password)
            )

            if (result.isSuccess) {
                val token = result.getOrNull()?.tokens?.access

                if (token != null) {
                    sessionManager.saveAuthToken(token)
                    loginState.value = "Login Successful ✅"
                } else {
                    loginState.value = "No token received ❌"
                }

            } else {
                loginState.value = "Error: ${result.exceptionOrNull()?.message}"
            }
        }
    }

    fun register(email: String, password: String, role: String) {
        viewModelScope.launch {

            val result = repository.registerUser(
                RegisterRequest(email, password, role)
            )

            if (result.isSuccess) {

                val token = result.getOrNull()?.tokens?.access

                if (token != null) {
                    sessionManager.saveAuthToken(token)
                    message.value = "Signup Successful ✅"
                    registerState.value=true
                } else {
                    message.value = "Token missing ❌"
                    registerState.value=false
                }

            } else {
                message.value = "Error: ${result.exceptionOrNull()?.message}"
                registerState.value=false
            }
        }
    }
}