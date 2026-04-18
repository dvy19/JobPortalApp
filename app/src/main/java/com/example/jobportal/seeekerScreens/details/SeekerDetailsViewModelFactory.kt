package com.example.jobportal.seeekerScreens.details


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class SeekerDetailsViewModelFactory(
    private val repository: SeekerProfileRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SeekerDetailsViewModel::class.java)) {
            return SeekerDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}