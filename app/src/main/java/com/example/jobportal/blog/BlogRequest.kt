package com.example.jobportal.blog

data class BlogResponse(
    val message: String,
    val data: BlogRequest
)

data class BlogRequest(
    val id: Int,
    val title: String,
    val description: String="",
    val created_at: String="",
    val updated_at: String="",
    val total_likes: Int=0,
    val is_liked: Boolean=false,
)