package com.example.jobportal.seeekerScreens.apply

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApplyJobInterface{

    @POST("api/jobs/apply/")   // match your Django URL exactly
    suspend fun applyJob(
        @Header("Authorization") token: String,
        @Body request: ApplyJobRequest
    ): Response<ApplyJobResponse>
}