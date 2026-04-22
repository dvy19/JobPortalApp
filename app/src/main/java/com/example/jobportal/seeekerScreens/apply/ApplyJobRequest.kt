package com.example.jobportal.seeekerScreens.apply

data class ApplyJobRequest(
    val job: Int
)


/*
{
    "id": 2,
    "status": "pending",
    "applied_at": "2026-04-22T15:44:02.328452Z",
    "job": 1,
    "applicant": 22
}
 */

data class ApplyJobResponse(

    val id:Int,
    val status:String,
    val applied_at:String,
    val job:Int,
    val applicant:Int
)