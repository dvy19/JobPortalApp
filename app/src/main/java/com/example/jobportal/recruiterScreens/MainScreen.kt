package com.example.jobportal.recruiterScreens

import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.rememberNavController

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jobportal.RecruiterBottomNavBar
import com.example.jobportal.Screens
import com.example.jobportal.recruiterScreens.blog.BlogScreen
import com.example.jobportal.recruiterScreens.dashboard.RecruiterHome
import com.example.jobportal.recruiterScreens.job.JobCreate

@Composable
fun MainScreen(rootNavController: NavController) {

    val mainNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            RecruiterBottomNavBar(mainNavController)
        }
    ) { paddingValues ->

        NavHost(
            navController = mainNavController,
            startDestination = Screens.RecruiterHome.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(Screens.RecruiterHome.route) {
                RecruiterHome(mainNavController)
            }

            composable(Screens.RecruiterPost.route) {
                RecruiterPost(mainNavController)
            }


            composable(Screens.RecruiterProfile.route) {
                RecruiterProfile()
            }

            composable(Screens.CreateBlog.route){
                BlogScreen()
            }

            composable(Screens.RecruiterJob.route){
                JobCreate()
            }






        }
    }
}
