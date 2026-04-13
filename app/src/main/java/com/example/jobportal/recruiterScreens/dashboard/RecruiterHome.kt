package com.example.jobportal.recruiterScreens.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jobportal.auth.SessionManager
import com.example.jobportal.recruiter_details.RecruiterDetailsViewModel
import com.example.jobportal.recruiter_details.RecruiterDetailsViewModelFactory
import com.example.jobportal.recruiter_details.RecruiterProfileRepository
import com.example.jobportal.recruiter_details.RecruiterProfileState

@Composable
fun RecruiterHome(
    mainNavController: NavController
){

    val context=LocalContext.current

    val sessionManager= SessionManager(context)
    val repository=RecruiterProfileRepository(sessionManager)
    val viewModel: RecruiterDetailsViewModel = viewModel(
        factory = RecruiterDetailsViewModelFactory(repository)
    )

    LaunchedEffect(Unit) {
        viewModel.getRecruiterProfile()
    }


    val state = viewModel.state.collectAsState()

    when (val result = state.value) {

        is RecruiterProfileState.Idle -> {
            // Initial state (before API call)
            Text("Welcome")
        }

        is RecruiterProfileState.Loading -> {
            Text("Loading profile...")
        }

        is RecruiterProfileState.Success -> {
            val profile = result.data

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "City: ${profile.city}")
                    Text(text = "State: ${profile.state}")
                }
            }
        }

        is RecruiterProfileState.Error -> {
            Text(text = "Error: ${result.message}")
        }
    }


}