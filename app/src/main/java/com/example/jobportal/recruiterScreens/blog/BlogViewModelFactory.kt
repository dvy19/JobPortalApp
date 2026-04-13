package com.example.jobportal.recruiterScreens.blog


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class BlogViewModelFactory(
    private val repository: BlogRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BlogViewModel::class.java)) {
            return BlogViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}