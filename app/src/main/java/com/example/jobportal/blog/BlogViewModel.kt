package com.example.jobportal.blog


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
class BlogViewModel(
    private val repository: BlogRepository
) : ViewModel() {

    private val _createBlogState = MutableStateFlow<BlogResponse?>(null)
    val createBlogState: StateFlow<BlogResponse?> = _createBlogState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun createBlog(title: String, description: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val response = repository.create_blog(
                    BlogRequest(
                        title = title,
                        description = description,
                        created_at = "",
                        updated_at = "",
                        total_likes = 0,
                        is_liked = false,
                        id = 0
                    )
                )

                if (response.isSuccessful) {
                    _createBlogState.value = response.body()
                } else {
                    _error.value = "Failed: ${response.code()}"
                }

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}