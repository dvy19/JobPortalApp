package com.example.jobportal.skills

data class AddSkillRequest(
    val name: String
)

data class Skill(
    val id: Int,
    val name: String
)

data class AddSkillResponse(
    val message: String,
    val data: Skill
)

