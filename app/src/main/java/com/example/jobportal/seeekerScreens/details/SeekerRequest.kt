package com.example.jobportal.seeekerScreens.details

data class SeekerRequest(
    val full_name:String,
    val gender:String,
    val city:String,
    val college_name:String,
    val state:String,
    val date_of_birth:String
)

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