package com.example.jobportal.recruiterScreens.post


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jobportal.recruiterScreens.post.PostRepository
import com.example.jobportal.recruiterScreens.post.PostViewModel


class PostViewModelFactory(
    private val repository: PostRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}