package com.example.jobportal.recruiterScreens.blog

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface BlogApiInterface{

    @POST("api/jobs/create_blogs/")

    suspend fun createBlog(
        @Header("Authorization") token: String,
        @Body request: BlogRequest
    ) : Response<BlogResponse>
}