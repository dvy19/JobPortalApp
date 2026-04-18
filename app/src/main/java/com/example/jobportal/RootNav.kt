package com.example.jobportal

import com.example.jobportal.auth.LoginScreen
import com.example.jobportal.auth.SignUpScreen



import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jobportal.recruiterScreens.MainScreen
import com.example.jobportal.recruiter_details.RecruiterDetailsScreen
import com.example.jobportal.seeekerScreens.SeekerMain
import com.example.jobportal.seeekerScreens.details.SeekerDetailsScreen

@Composable
fun RootNav(innerPadding: PaddingValues) {
    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination ="signup"
    ) {




        composable(Screens.Login.route) {
            LoginScreen(rootNavController)
        }

        composable("signup") {
            SignUpScreen(rootNavController)
        }

        composable(Screens.RecruiterDetails.route){
            RecruiterDetailsScreen(rootNavController)
        }

        composable(Screens.SeekerDetails.route){
            SeekerDetailsScreen(rootNavController)
        }



        composable(Screens.Main.route){
            MainScreen(rootNavController)
        }

        composable(Screens.SeekerMain.route){
            SeekerMain(rootNavController)
        }



    }
}
