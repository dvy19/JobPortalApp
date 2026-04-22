package com.example.jobportal.auth

import com.example.jobportal.recruiterScreens.blog.BlogApiInterface
import com.example.jobportal.recruiterScreens.job.JobApiInterface
import com.example.jobportal.recruiterScreens.post.PostApiInterface
import com.example.jobportal.recruiter_details.RecruiterDetailApi
import com.example.jobportal.seeekerScreens.apply.ApplyJobInterface
import com.example.jobportal.seeekerScreens.details.SeekerDetailApi
import retrofit2.Retrofit
import com.example.jobportal.skills.SkillsDetailApi
import retrofit2.converter.gson.GsonConverterFactory
object JobPortalApiClient {

    private const val BASE_URL = "https://job-portal-django-1-rc3u.onrender.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // ✅ Register API
    val registerApi: RegisterApiInterface by lazy {
        retrofit.create(RegisterApiInterface::class.java)
    }

    // ✅ Recruiter API (ADD THIS)
    val recruiterApi: RecruiterDetailApi by lazy {
        retrofit.create(RecruiterDetailApi::class.java)
    }

    val blogApi: BlogApiInterface by lazy {
        retrofit.create(BlogApiInterface::class.java)
    }

    val seekerApi: SeekerDetailApi by lazy{
        retrofit.create(SeekerDetailApi::class.java)
    }

    val skillApi:SkillsDetailApi by lazy{
        retrofit.create(SkillsDetailApi::class.java)
    }

    val postApi: PostApiInterface by lazy {
        retrofit.create(PostApiInterface::class.java)
    }

    val JobApi: JobApiInterface by lazy {
        retrofit.create(JobApiInterface::class.java)
    }

    val ApplyJobApi: ApplyJobInterface by lazy{
        retrofit.create(ApplyJobInterface::class.java)
    }
}