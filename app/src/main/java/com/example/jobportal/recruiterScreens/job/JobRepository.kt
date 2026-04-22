package com.example.jobportal.recruiterScreens.job

import com.example.jobportal.auth.JobPortalApiClient
import com.example.jobportal.auth.SessionManager
import retrofit2.Response
import kotlin.contracts.Returns

class JobRepository(
    private val sessionManager: SessionManager
){

    private val apiService: JobApiInterface= JobPortalApiClient.JobApi

    suspend fun create_job(request: JobRequest) : Response<JobResponse>{

        val token=sessionManager.getAuthToken()

        return apiService.createJob(
            token="Bearer $token",
            request=request
        )

    }

    suspend fun getJob():Response<JobListResponse>{
        val token=sessionManager.getAuthToken()

        return apiService.getJob(
            token="Bearer $token"
        )
    }

    suspend fun GetSingleJob(id:Int): Response<JobResponse>{

        val token=sessionManager.getAuthToken()

        return apiService.getSingleJob(
            token="Bearer $token",
            id=id
        )
    }

}