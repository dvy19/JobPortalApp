package com.example.jobportal.recruiterScreens.blog

data class BlogResponse(
    val message: String,
    val data: BlogRequest
)

data class BlogRequest(
    val title: String,
    val description: String="",
)

/*
{
    "message": "Blog created successfully",
    "data": {
        "title": "siddharth22@gmail.com",
        "description": "recruiter"
    }
}
 */