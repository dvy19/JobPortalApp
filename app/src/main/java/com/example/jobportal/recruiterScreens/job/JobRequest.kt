package com.example.jobportal.recruiterScreens.job

data class JobRequest(
    val title: String,
    val description: String,
    val location:String,
    val stipend: Float

)

data class JobResponse(
    val message:String,
    val data: AfterJobRequest
)

data class AfterJobRequest(
    val title:String,
    val description:String,
    val location:String,
    val stipend:Float,
    val created_at:String,
    val updated_at:String,
    val user:Int,
    val id:Int
)

/*
{
    "message": "Job created successfully",
    "data": {
        "id": 1,
        "title": "internship",
        "description": "djnciedc ed cedcnedc eiwdncoew co",
        "location": "remote",
        "stipend": "0.00",
        "created_at": "2026-04-12T14:47:40.035098Z",
        "updated_at": "2026-04-12T14:47:40.035133Z",
        "user": 1
    }
}
 */




