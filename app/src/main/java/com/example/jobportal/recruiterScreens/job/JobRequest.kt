package com.example.jobportal.recruiterScreens.job

data class JobRequest(
    val title: String,
    val description: String,
    val location:String,
    val stipend: Float

)


data class JobResponse(
    val id: Int,
    val skill_names: List<String>,
    val title: String,
    val description: String,
    val location: String,
    val stipend: String,
    val created_at: String,
    val updated_at: String,
    val user: Int
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



// get job by id
/*

{
    "id": 1,
    "skill_names": [],
    "title": "vibrations20",
    "description": "vibration",
    "location": "remote",
    "stipend": "0.00",
    "created_at": "2026-04-14T16:27:50.335677Z",
    "updated_at": "2026-04-14T16:27:50.335708Z",
    "user": 12
}

 */


