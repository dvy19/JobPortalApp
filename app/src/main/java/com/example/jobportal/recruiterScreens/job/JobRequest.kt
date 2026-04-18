package com.example.jobportal.recruiterScreens.job

data class JobRequest(
    val title: String,
    val description: String,
    val location:String,
    val stipend: Float

)



data class JobResponse(
    val title:String,
    val description:String,
    val location:String,
    val stipend:Float,
    val created_at:String,
    val updated_at:String,
    val user:Int,
    val id:Int
)

data class JobListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<JobResponse>
)

/*
{
    "id": 1,
    "title": "vibrations20",
    "description": "vibration",
    "location": "remote",
    "stipend": "0.00",
    "created_at": "2026-04-14T16:27:50.335677Z",
    "updated_at": "2026-04-14T16:27:50.335708Z",
    "user": 12
}

 */




