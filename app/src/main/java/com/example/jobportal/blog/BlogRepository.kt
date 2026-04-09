package com.example.jobportal.blog

import com.example.jobportal.auth.JobPortalApiClient
import com.example.jobportal.auth.SessionManager
import retrofit2.Response

class BlogRepository(
    private val sessionManager: SessionManager
){

    private val apiService : BlogApiInterface= JobPortalApiClient.apiService as BlogApiInterface

    suspend fun create_blog(
        request: BlogRequest
    ): Response<BlogResponse> {
        // 1. Fetch the token
        val token = sessionManager.getAuthToken()
            ?: throw IllegalStateException("User is not authenticated")

        // 2. Make the API call
        return apiService.createBlog(
            token = "Bearer $token",
            request = request
        )
    }
}