package com.example.jobportal.recruiter_details

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RecruiterDetailApi {
    @POST("api/accounts/recruiter-profile/") // Replace with your actual endpoint path
    suspend fun createRecruiterProfile(
        @Header("Authorization") token: String,
        @Body request: RecruiterProfileRequest
    ): Response<RecruiterProfileResponse>


    @GET("api/recruiter/profile/")
    suspend fun getRecruiterProfile(
        @Header("Authorization") token: String
    ): Response<RecruiterProfileResponse>
}