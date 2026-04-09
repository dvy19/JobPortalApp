package com.example.jobportal.auth

class UserRepository {

    suspend fun registerUser(request: RegisterRequest): Result<RegisterResponse> {
        return try {
            val response = JobPortalApiClient.apiService.registerUser(request)

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun loginUser(request: LoginRequest): Result<LoginResponse> {
        return try {
            val response = JobPortalApiClient.apiService.loginUser(request)

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}