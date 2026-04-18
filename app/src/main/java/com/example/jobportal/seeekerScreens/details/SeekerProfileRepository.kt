package com.example.jobportal.seeekerScreens.details

import android.util.Log
import com.example.jobportal.auth.JobPortalApiClient
import com.example.jobportal.auth.SessionManager
import com.example.jobportal.recruiter_details.RecruiterProfileRequest
import com.example.jobportal.recruiter_details.RecruiterProfileResponse
import retrofit2.Response


class SeekerProfileRepository(
    private val sessionManager: SessionManager
) {

    private val apiService = JobPortalApiClient.seekerApi

    // 🔹 Create Recruiter Profile
    suspend fun createSeekerProfile(
        request: SeekerRequest
    ): Response<SeekerResponse> {

        val token = sessionManager.getAuthToken()

        Log.d("TOKEN_CHECK", token ?: "NULL")

        return apiService.createSeekerProfile(
            token = "Bearer $token",
            request = request
        )

        Log.d("FINAL_HEADER", "Bearer $token")
    }


    suspend fun getSeekerProfile(): Response<SeekerData> {

        val token = sessionManager.getAuthToken()

        if (token.isNullOrEmpty()) {
            throw Exception("User not authenticated")
        }

        return apiService.getSeekerProfile(
            token = "Bearer $token"
        )
    }
}