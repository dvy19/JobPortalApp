package com.example.jobportal.recruiterScreens.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobportal.recruiterScreens.blog.BlogRepository
import com.example.jobportal.recruiterScreens.blog.BlogRequest
import com.example.jobportal.recruiterScreens.blog.BlogResponse
import com.example.jobportal.recruiterScreens.blog.CreateBlogUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class CreatePostUiState {
    object Idle : CreatePostUiState()
    object Loading : CreatePostUiState()
    data class Success(val data: PostResponse) : CreatePostUiState()
    data class Error(val message: String) : CreatePostUiState()
}

sealed class PostUiState {
    object Loading : PostUiState()
    data class Success(val posts: List<PostResponse>) : PostUiState()
    data class Error(val message: String) : PostUiState()
}


class PostViewModel(
    private val repository: PostRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CreatePostUiState>(CreatePostUiState.Idle)
    val uiState: StateFlow<CreatePostUiState> = _uiState

    private val _postUiState = MutableStateFlow<PostUiState>(PostUiState.Loading)
    val postUiState: StateFlow<PostUiState> = _postUiState

    fun createPost(title: String, description: String) {
        viewModelScope.launch {
            _uiState.value = CreatePostUiState.Loading

            try {
                val response = repository.create_post(
                    PostRequest(title, description)
                )

                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = CreatePostUiState.Success(response.body()!!)
                } else {
                    _uiState.value = CreatePostUiState.Error("Failed: ${response.code()}")
                }

            } catch (e: Exception) {
                _uiState.value = CreatePostUiState.Error(e.message ?: "Unknown error")
            }
        }
    }


    fun fetchPosts() {
        viewModelScope.launch {

            _postUiState.value = PostUiState.Loading

            try {
                val response = repository.getPosts()

                if (response.isSuccessful && response.body() != null) {
                    _postUiState.value = PostUiState.Success(response.body()!!)
                } else if(response.body()==null){
                    _postUiState.value = PostUiState.Error("No post to display")
                }else{

                    _postUiState.value = PostUiState.Error("Failed to load posts")
                }

            } catch (e: Exception) {
                _postUiState.value = PostUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
    // ✅ VERY IMPORTANT (reset after navigation)
    fun resetState() {
        _uiState.value = CreatePostUiState.Idle
    }
}