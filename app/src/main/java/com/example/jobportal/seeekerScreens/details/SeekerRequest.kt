package com.example.jobportal.seeekerScreens.details

data class SeekerRequest(
    val full_name:String,
    val gender:String,
    val city:String,
    val college_name:String,
    val state:String,
    val date_of_birth:String,
    val skills:List<String>
)

/*
{
    "full_name":"seeker 555",
    "college_name":"college555",
    "gender":"male",
    "date_of_birth":"2003-12-12",
    "city":"kanput",
    "state":"Uttar preadesh",
    "skills":[]
}
 */

/*
{
    "message": "Job seeker profile created successfully",
    "data": {
        "id": 20,
        "skills": [],
        "full_name": "seeker 555",
        "gender": "male",
        "date_of_birth": "2003-12-12",
        "college_name": "college555",
        "city": "kanput",
        "state": "Uttar preadesh",
        "user": 57
    }
}
 */

data class SeekerResponse(
    val message:String,
    val data:SeekerData
)

data class SeekerData(
    val full_name:String,
    val gender:String,
    val city:String,
    val college_name:String,
    val state:String,
    val date_of_birth:String
)