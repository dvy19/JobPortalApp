package com.example.jobportal.recruiterScreens.job


import com.example.jobportal.recruiterScreens.blog.BlogRequest
import com.example.jobportal.recruiterScreens.blog.BlogResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface JobApiInterface{

    @POST("api/jobs/recruiter/create_job/")

    suspend fun createJob(
        @Header("Authorization") token: String,
        @Body request: JobRequest
    ) : Response<JobResponse>
}