package com.example.jobportal.auth

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object JobPortalApiClient {

    private const val BASE_URL = "https://job-portal-django-1-rc3u.onrender.com/"

    val apiService: RegisterApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RegisterApiInterface::class.java)
    }
}