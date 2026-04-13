package com.example.jobportal.recruiterScreens.job

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobportal.recruiterScreens.blog.BlogRequest
import com.example.jobportal.recruiterScreens.blog.BlogResponse
import com.example.jobportal.recruiterScreens.blog.CreateBlogUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class CreateJobUiState {
    object Idle : CreateJobUiState()
    object Loading : CreateJobUiState()
    data class Success(val data: JobResponse) : CreateJobUiState()
    data class Error(val message: String) : CreateJobUiState()
}


class JobViewModel(
    private val repository: JobRepository
) :ViewModel(){

    private val _uiState = MutableStateFlow<CreateJobUiState>(CreateJobUiState.Idle)
    val uiState: StateFlow<CreateJobUiState> = _uiState

    fun createJob(title: String, description: String,location:String,stipend:Float) {
        viewModelScope.launch {
            _uiState.value = CreateJobUiState.Loading

            try {
                val response = repository.create_job(
                    JobRequest(title, description,location,stipend)
                )

                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = CreateJobUiState.Success(response.body()!!)
                } else {
                    _uiState.value = CreateJobUiState.Error("Failed: ${response.code()}")
                }

            } catch (e: Exception) {
                _uiState.value = CreateJobUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    // ✅ VERY IMPORTANT (reset after navigation)
    fun resetState() {
        _uiState.value = CreateJobUiState.Idle
    }



}