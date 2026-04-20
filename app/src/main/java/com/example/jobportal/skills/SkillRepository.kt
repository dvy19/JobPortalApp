package com.example.jobportal.skills

import android.util.Log
import com.example.jobportal.auth.JobPortalApiClient
import com.example.jobportal.auth.SessionManager
import com.example.jobportal.recruiter_details.RecruiterProfileRequest
import com.example.jobportal.recruiter_details.RecruiterProfileResponse
import com.example.jobportal.seeekerScreens.details.SeekerData
import retrofit2.Response


class SkillRepository(
    private val sessionManager: SessionManager
) {

    private val apiService = JobPortalApiClient.skillApi

    // 🔹 Create Recruiter Profile
    suspend fun addSeekerSkill(
        request: AddSkillRequest
    ): Response<AddSkillResponse> {

        val token = sessionManager.getAuthToken()

        Log.d("TOKEN_CHECK", token ?: "NULL")

        return apiService.addSkill(
            token = "Bearer $token",
            request = request
        )

        Log.d("FINAL_HEADER", "Bearer $token")
    }


    suspend fun getSeekerSkill(): Response<List<Skill>> {

        val token = sessionManager.getAuthToken()

        if (token.isNullOrEmpty()) {
            throw Exception("User not authenticated")
        }

        return apiService.getSeekerSkill(
            token = "Bearer $token"
        )
    }
}