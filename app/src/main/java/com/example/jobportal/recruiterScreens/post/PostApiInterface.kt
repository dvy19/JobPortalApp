package com.example.jobportal.recruiterScreens.post


import com.example.jobportal.recruiterScreens.blog.BlogRequest
import com.example.jobportal.recruiterScreens.blog.BlogResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface PostApiInterface{

    @POST("api/jobs/recruiter/create_post")

    suspend fun createPost(
        @Header("Authorization") token: String,
        @Body request: PostRequest
    ) : Response<PostResponse>

    @GET("api/jobs/recruiter/create_post")
    suspend fun getPosts(
        @Header("Authorization") token: String
    ): Response<List<PostResponse>>
}