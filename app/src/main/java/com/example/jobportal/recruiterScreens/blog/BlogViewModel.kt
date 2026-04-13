package com.example.jobportal.recruiterScreens.blog


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
sealed class CreateBlogUiState {
    object Idle : CreateBlogUiState()
    object Loading : CreateBlogUiState()
    data class Success(val data: BlogResponse) : CreateBlogUiState()
    data class Error(val message: String) : CreateBlogUiState()
}

class BlogViewModel(
    private val repository: BlogRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CreateBlogUiState>(CreateBlogUiState.Idle)
    val uiState: StateFlow<CreateBlogUiState> = _uiState

    fun createBlog(title: String, description: String) {
        viewModelScope.launch {
            _uiState.value = CreateBlogUiState.Loading

            try {
                val response = repository.create_blog(
                    BlogRequest(title, description)
                )

                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = CreateBlogUiState.Success(response.body()!!)
                } else {
                    _uiState.value = CreateBlogUiState.Error("Failed: ${response.code()}")
                }

            } catch (e: Exception) {
                _uiState.value = CreateBlogUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    // ✅ VERY IMPORTANT (reset after navigation)
    fun resetState() {
        _uiState.value = CreateBlogUiState.Idle
    }
}