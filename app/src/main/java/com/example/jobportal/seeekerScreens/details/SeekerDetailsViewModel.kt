package com.example.jobportal.seeekerScreens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobportal.recruiter_details.RecruiterProfileData

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class SeekerProfileState {
    object Idle : SeekerProfileState()
    object Loading : SeekerProfileState()
    data class Success(val data: SeekerData) : SeekerProfileState()
    data class Error(val message: String) : SeekerProfileState()
}


class SeekerDetailsViewModel(
    private val repository: SeekerProfileRepository
) : ViewModel() {

    private val _state = MutableStateFlow<SeekerProfileState>(SeekerProfileState.Idle)
    val state: StateFlow<SeekerProfileState> = _state

    // 🔹 Create Profile
    fun createSeekerProfile(request: SeekerRequest) {
        viewModelScope.launch {

            _state.value = SeekerProfileState.Loading

            try {
                val response = repository.createSeekerProfile(request)

                if (response.isSuccessful && response.body() != null) {
                    _state.value = SeekerProfileState.Success(
                        response.body()!!.data
                    )
                } else {
                    _state.value = SeekerProfileState.Error(
                        "Error: ${response.code()}"
                    )
                }

            } catch (e: Exception) {
                _state.value = SeekerProfileState.Error(
                    e.message ?: "Something went wrong"
                )
            }
        }
    }

    // 🔹 Get Profile
    fun getSeekerProfile() {
        viewModelScope.launch {

            _state.value = SeekerProfileState.Loading

            try {
                val response = repository.getSeekerProfile()

                if (response.isSuccessful && response.body() != null) {
                    _state.value = SeekerProfileState.Success(
                        response.body()!!
                    )
                } else {
                    _state.value = SeekerProfileState.Error(
                        "Error: ${response.code()}"
                    )
                }

            } catch (e: Exception) {
                _state.value = SeekerProfileState.Error(
                    e.message ?: "Something went wrong"
                )
            }
        }
    }
}