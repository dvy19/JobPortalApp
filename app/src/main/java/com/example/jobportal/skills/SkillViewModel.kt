package com.example.jobportal.skills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobportal.recruiter_details.RecruiterProfileData
import com.example.jobportal.recruiter_details.RecruiterProfileRequest
import com.example.jobportal.recruiter_details.RecruiterProfileState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class SkillState {
    object Idle : SkillState()
    object Loading : SkillState()
    data class Success(val data: Skill) : SkillState()
    data class Error(val message: String) : SkillState()
}

class SkillViewModel(
    private val repository: SkillRepository
): ViewModel(){

    private val _state = MutableStateFlow<SkillState>(SkillState.Idle)
    val state: StateFlow<SkillState> = _state

    fun createSkill(request: AddSkillRequest) {
        viewModelScope.launch {

            _state.value = SkillState.Loading

            try {
                val response = repository.addSeekerSkill(request)

                if (response.isSuccessful && response.body() != null) {
                    _state.value = SkillState.Success(
                        response.body()!!.data
                    )
                } else {
                    _state.value = SkillState.Error(
                        "Error: ${response.code()}"
                    )
                }

            } catch (e: Exception) {
                _state.value = SkillState.Error(
                    e.message ?: "Something went wrong"
                )
            }
        }
    }

    fun getSkill() {
        viewModelScope.launch {

            _state.value = SkillState.Loading

            try {
                val response = repository.getSeekerSkill()

                if (response.isSuccessful && response.body() != null) {
                    _state.value = SkillState.Success(
                        response.body()!!
                    )
                } else {
                    _state.value = SkillState.Error(
                        "Error: ${response.code()}"
                    )
                }

            } catch (e: Exception) {
                _state.value = SkillState.Error(
                    e.message ?: "Something went wrong"
                )
            }
        }


    }
}