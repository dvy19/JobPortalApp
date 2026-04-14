package com.example.jobportal.recruiterScreens.post

import com.example.jobportal.auth.JobPortalApiClient
import com.example.jobportal.auth.SessionManager
import com.example.jobportal.recruiterScreens.blog.BlogApiInterface
import com.example.jobportal.recruiterScreens.blog.BlogRequest
import com.example.jobportal.recruiterScreens.blog.BlogResponse
import retrofit2.Response

class PostRepository(
    private val sessionManager: SessionManager
){

    private val apiService : PostApiInterface= JobPortalApiClient.postApi

    suspend fun create_post(
        request: PostRequest
    ): Response<PostResponse> {
        // 1. Fetch the token
        val token = sessionManager.getAuthToken()
            ?: throw IllegalStateException("User is not authenticated")

        // 2. Make the API call
        return apiService.createPost(
            token = "Bearer $token",
            request = request
        )
    }

    suspend fun getPosts(): Response<List<PostResponse>> {

        val token = sessionManager.getAuthToken()
            ?: throw IllegalStateException("User is not authenticated")

        return apiService.getPosts(
            token = "Bearer $token"
        )
    }
}