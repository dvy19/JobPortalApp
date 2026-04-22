package com.example.jobportal.seeekerScreens.apply


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jobportal.recruiter_details.RecruiterDetailsViewModel
import com.example.jobportal.recruiter_details.RecruiterProfileRepository

class ApplyJobFactory(
    private val repository: ApplyJobRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ApplyJobViewModel::class.java)) {
            return ApplyJobViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}