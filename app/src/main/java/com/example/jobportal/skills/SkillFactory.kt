package com.example.jobportal.skills


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jobportal.recruiter_details.RecruiterDetailsViewModel
import com.example.jobportal.recruiter_details.RecruiterProfileRepository

class SkillFactory(
    private val repository: SkillRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SkillViewModel::class.java)) {
            return SkillViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}