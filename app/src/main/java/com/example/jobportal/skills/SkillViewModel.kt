package com.example.jobportal.skills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class SkillState {
    object Idle : SkillState()
    object Loading : SkillState()
    data class Success(val data: Skill) : SkillState()
    data class Error(val message: String) : SkillState()
}

sealed class GetSkillState{
    object Idle:GetSkillState()
    object Loading: GetSkillState()
    data class Success(val data:List<Skill>): GetSkillState()
    data class Error(val message: String): GetSkillState()

}

class SkillViewModel(
    private val repository: SkillRepository
): ViewModel(){

    private val _state = MutableStateFlow<SkillState>(SkillState.Idle)
    val state: StateFlow<SkillState> = _state

    private val _skillState= MutableStateFlow<GetSkillState>(GetSkillState.Idle)
    val skillState:StateFlow<GetSkillState> = _skillState

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

            _skillState.value = GetSkillState.Loading

            try {
                val response = repository.getSeekerSkill()
                val data = response.body()

                if (response.isSuccessful && data != null) {
                    _skillState.value = GetSkillState.Success(data)
                } else {
                    _skillState.value = GetSkillState.Error(
                        "Error: ${response.code()}"
                    )
                }

            } catch (e: Exception) {
                _skillState.value = GetSkillState.Error(
                    e.message ?: "Something went wrong"
                )
            }
        }
    }


    }
