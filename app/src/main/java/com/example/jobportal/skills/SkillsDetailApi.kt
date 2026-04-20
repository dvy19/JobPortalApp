package com.example.jobportal.skills

import com.example.jobportal.seeekerScreens.details.SeekerData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface SkillsDetailApi{

    @POST("api/accounts/skills/")
    suspend fun addSkill(
        @Header("Authorization") token:String,
        @Body request:AddSkillRequest
    ):Response<AddSkillResponse>


    @GET("api/accounts/skills/")
    suspend fun getSeekerSkill(
        @Header("Authorization") token:String
    ):Response<Skill>


}