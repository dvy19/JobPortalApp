package com.example.jobportal.recruiter_details



import android.app.Application
import com.example.jobportal.auth.JobPortalApiClient
import com.example.jobportal.auth.SessionManager
import retrofit2.Response


class RecruiterProfileRepository(
    private val sessionManager: SessionManager
    ) {

    private val apiService = JobPortalApiClient.recruiterApi

        // 🔹 Create Recruiter Profile
        suspend fun createRecruiterProfile(
            request: RecruiterProfileRequest
        ): Response<RecruiterProfileResponse> {

            val token = sessionManager.getAuthToken()

            return apiService.createRecruiterProfile(
                token = "Bearer $token",
                request = request
            )
        }


    suspend fun getRecruiterProfile(): Response<RecruiterProfileResponse> {

        val token = sessionManager.getAuthToken()

        if (token.isNullOrEmpty()) {
            throw Exception("User not authenticated")
        }

        return apiService.getRecruiterProfile(
            token = "Bearer $token"
        )
    }
    }
