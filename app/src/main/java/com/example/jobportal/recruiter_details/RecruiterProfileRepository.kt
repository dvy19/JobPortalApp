package com.example.jobportal.recruiter_details



import android.app.Application
import android.util.Log
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

            Log.d("TOKEN_CHECK", token ?: "NULL")

            return apiService.createRecruiterProfile(
                token = "Bearer $token",
                request = request
            )

            Log.d("FINAL_HEADER", "Bearer $token")
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
