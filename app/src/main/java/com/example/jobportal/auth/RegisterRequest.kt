package com.example.jobportal.auth

data class RegisterRequest(
    val email: String,
    val password: String,
    val role: String
)

data class RegisterResponse(
    val message: String,
    val role: String,
    val tokens: Tokens
)

data class Tokens(
    val access: String,
    val refresh: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String,
    val role: String,
    val tokens: Tokens
)


