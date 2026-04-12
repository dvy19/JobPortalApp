package com.example.jobportal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jobportal.ui.theme.JobPortalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JobPortalTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RootNav(innerPadding)

                }
            }
        }
    }
}


sealed class Screens(val route: String) {
   data object SignUp:Screens("signup")
    data object Login:Screens("login")
    data object RecruiterDetails:Screens("recruiterDetails")
    data object SeekerDashboard:Screens("SeekerDashboard")

    data object CreateBlog:Screens("createBlog")
    data object RecruiterHome:Screens("recruiterHome")
    data object RecruiterPost:Screens("recruiterPost")
    data object RecruiterJob:Screens("recruiterJob")
    data object RecruiterProfile:Screens("recruiterProfile")

    data object Main:Screens("main")



}


