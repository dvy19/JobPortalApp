package com.example.jobportal.recruiter_details

data class RecruiterProfileRequest(
    val company_name: String,
    val full_name: String,
    val position: String,
    val city: String,
    val state: String
)
data class RecruiterProfileResponse(
    val message: String,
    val data: RecruiterProfileData
)

data class RecruiterProfileData(
    val id: Int,
    val user: Int,
    val company_name: String,
    val full_name: String,
    val position: String,
    val city: String,
    val state: String
)