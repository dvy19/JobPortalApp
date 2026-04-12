package com.example.jobportal.recruiter_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class RecruiterProfileState {
    object Idle : RecruiterProfileState()
    object Loading : RecruiterProfileState()
    data class Success(val data: RecruiterProfileData) : RecruiterProfileState()
    data class Error(val message: String) : RecruiterProfileState()
}

class RecruiterDetailsViewModel(
    private val repository: RecruiterProfileRepository
) : ViewModel() {

    private val _state = MutableStateFlow<RecruiterProfileState>(RecruiterProfileState.Idle)
    val state: StateFlow<RecruiterProfileState> = _state

    // 🔹 Create Profile
    fun createRecruiterProfile(request: RecruiterProfileRequest) {
        viewModelScope.launch {

            _state.value = RecruiterProfileState.Loading

            try {
                val response = repository.createRecruiterProfile(request)

                if (response.isSuccessful && response.body() != null) {
                    _state.value = RecruiterProfileState.Success(
                        response.body()!!.data
                    )
                } else {
                    _state.value = RecruiterProfileState.Error(
                        "Error: ${response.code()}"
                    )
                }

            } catch (e: Exception) {
                _state.value = RecruiterProfileState.Error(
                    e.message ?: "Something went wrong"
                )
            }
        }
    }

    // 🔹 Get Profile
    fun getRecruiterProfile() {
        viewModelScope.launch {

            _state.value = RecruiterProfileState.Loading

            try {
                val response = repository.getRecruiterProfile()

                if (response.isSuccessful && response.body() != null) {
                    _state.value = RecruiterProfileState.Success(
                        response.body()!!.data
                    )
                } else {
                    _state.value = RecruiterProfileState.Error(
                        "Error: ${response.code()}"
                    )
                }

            } catch (e: Exception) {
                _state.value = RecruiterProfileState.Error(
                    e.message ?: "Something went wrong"
                )
            }
        }
    }
}