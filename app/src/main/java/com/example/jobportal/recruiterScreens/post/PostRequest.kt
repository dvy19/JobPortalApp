package com.example.jobportal.recruiterScreens.post

data class PostRequest(
    val title:String,
    val description:String
)
data class PostResponse(
    val id: Int,
    val user: String,
    val title: String,
    val description: String,
    val created_at: String,
    val full_name: String,
    val company_name: String?,   // nullable (for seekers)
    val comments: List<Comment>,
    val likes_count: Int
)

data class Comment(
    val id: Int,
    val content: String,
    val created_at: String
)