package com.example.jobportal.seeekerScreens.details

import com.example.jobportal.recruiter_details.RecruiterProfileRequest
import com.example.jobportal.recruiter_details.RecruiterProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface SeekerDetailApi {

    @POST("api/accounts/profile/") // Replace with your actual endpoint path
    suspend fun createSeekerProfile(
        @Header("Authorization") token: String,
        @Body request: SeekerRequest
    ): Response<SeekerResponse>


    @GET("api/accounts/profile/")
    suspend fun getSeekerProfile(
        @Header("Authorization") token: String
    ): Response<SeekerData>
}