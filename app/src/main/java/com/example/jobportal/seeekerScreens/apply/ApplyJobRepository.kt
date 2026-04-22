package com.example.jobportal.seeekerScreens.apply

import com.example.jobportal.auth.JobPortalApiClient
import com.example.jobportal.auth.SessionManager
class ApplyJobRepository(

    private val sessionManager: SessionManager
) {

    private val api= JobPortalApiClient.ApplyJobApi

    suspend fun applyJob(jobId: Int): Result<ApplyJobResponse> {

        return try {
            val token = sessionManager.getAuthToken()

            if (token.isNullOrEmpty()) {
                return Result.failure(Exception("User not authenticated"))
            }

            val request = ApplyJobRequest(job = jobId)

            val response = api.applyJob(
                token = "Bearer $token",
                request = request
            )

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(
                    Exception("Error: ${response.code()} ${response.message()}")
                )
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}