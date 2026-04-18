package com.example.jobportal.seeekerScreens

import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.rememberNavController

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jobportal.Screens
import com.example.jobportal.recruiterScreens.blog.BlogScreen
import com.example.jobportal.seeekerScreens.dashboard.DashBoardScreen

@Composable
fun SeekerMain(rootNavController: NavController) {

    val mainNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            SeekerBottomNav(mainNavController)
        }
    ) { paddingValues ->

        NavHost(
            navController = mainNavController,
            startDestination = Screens.SeekerDashboard.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(Screens.SeekerDashboard.route) {
                DashBoardScreen(mainNavController)
            }

            composable(Screens.SeekerPost.route) {
                PostScreen(mainNavController)
            }


            composable(Screens.SeekerProfile.route) {
                ProfileScreen(mainNavController)
            }

            composable(Screens.SeekerBlog.route){
                BlogScreen()
            }


        }
    }
}
