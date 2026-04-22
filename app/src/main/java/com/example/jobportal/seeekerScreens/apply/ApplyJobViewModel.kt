package com.example.jobportal.seeekerScreens.apply

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ApplyJobUiState {

    object Idle : ApplyJobUiState()

    object Loading : ApplyJobUiState()

    data class Success(
        val response: ApplyJobResponse
    ) : ApplyJobUiState()

    data class Error(
        val message: String
    ) : ApplyJobUiState()
}

class ApplyJobViewModel(
    private val repository: ApplyJobRepository
) : ViewModel() {

    private val _applyJobState = MutableStateFlow<ApplyJobUiState>(ApplyJobUiState.Idle)
    val applyJobState: StateFlow<ApplyJobUiState> = _applyJobState

    fun apply_job(jobId: Int) {

        viewModelScope.launch {

            _applyJobState.value = ApplyJobUiState.Loading

            val result = repository.applyJob(jobId)

            result.onSuccess { data ->
                _applyJobState.value = ApplyJobUiState.Success(data)
                Log.d("message",_applyJobState.value.toString())
            }.onFailure { error ->
                Log.d("message",_applyJobState.value.toString())

                _applyJobState.value = ApplyJobUiState.Error(
                    error.message ?: "Something went wrong"

                )
            }
        }
    }

    // Optional: reset state after success (useful for UI)
    fun resetState() {
        _applyJobState.value = ApplyJobUiState.Idle
    }
}