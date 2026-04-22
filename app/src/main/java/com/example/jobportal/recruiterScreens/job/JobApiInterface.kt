package com.example.jobportal.recruiterScreens.job


import com.example.jobportal.recruiterScreens.blog.BlogRequest
import com.example.jobportal.recruiterScreens.blog.BlogResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


interface JobApiInterface{

    @POST("api/jobs/recruiter/create_job/")

    suspend fun createJob(
        @Header("Authorization") token: String,
        @Body request: JobRequest
    ) : Response<JobResponse>

    @GET("api/jobs/recruiter/create_job/")
    suspend fun getJob(
        @Header("Authorization") token:String,
    ):Response<JobListResponse>

    @GET("api/jobs/getJob/{id}/")
    suspend fun getSingleJob(
        @Header("Authorization") token:String,
        @Path("id") id: Int
    ): Response<JobResponse>

    
}