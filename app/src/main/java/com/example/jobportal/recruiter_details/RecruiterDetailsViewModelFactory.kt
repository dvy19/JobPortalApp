package com.example.jobportal.recruiter_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RecruiterDetailsViewModelFactory(
    private val repository: RecruiterProfileRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecruiterDetailsViewModel::class.java)) {
            return RecruiterDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}