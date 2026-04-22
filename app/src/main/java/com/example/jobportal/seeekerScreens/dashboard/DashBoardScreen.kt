package com.example.jobportal.seeekerScreens.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jobportal.auth.SessionManager
import com.example.jobportal.recruiterScreens.dashboard.JobHorizontalList
import com.example.jobportal.recruiterScreens.dashboard.PostHorizontalList
import com.example.jobportal.recruiterScreens.job.JobRepository
import com.example.jobportal.recruiterScreens.job.JobUiState
import com.example.jobportal.recruiterScreens.job.JobViewModel
import com.example.jobportal.recruiterScreens.job.JobViewModelFactory
import com.example.jobportal.recruiterScreens.post.PostRepository
import com.example.jobportal.recruiterScreens.post.PostUiState
import com.example.jobportal.recruiterScreens.post.PostViewModel
import com.example.jobportal.recruiterScreens.post.PostViewModelFactory
import com.example.jobportal.recruiter_details.RecruiterProfileState
import com.example.jobportal.seeekerScreens.details.SeekerDetailsViewModel
import com.example.jobportal.seeekerScreens.details.SeekerDetailsViewModelFactory
import com.example.jobportal.seeekerScreens.details.SeekerProfileRepository
import com.example.jobportal.seeekerScreens.details.SeekerProfileState

@Composable
fun DashBoardScreen(
    mainNavController: NavController
){

    val context = LocalContext.current

    val sessionManager = SessionManager(context)
    val repository = SeekerProfileRepository(sessionManager)
    val viewModel: SeekerDetailsViewModel = viewModel(
        factory = SeekerDetailsViewModelFactory(repository)
    )

    val postRepo = PostRepository(sessionManager)
    val postViewModel: PostViewModel = viewModel(
        factory = PostViewModelFactory(postRepo)
    )

    val jobRepo= JobRepository(sessionManager)
    val jobViewModel: JobViewModel=viewModel(
        factory = JobViewModelFactory(jobRepo)
    )

    LaunchedEffect(Unit) {
        viewModel.getSeekerProfile()
        postViewModel.fetchPosts()
        jobViewModel.fetchJob()
    }

    val postUiState by postViewModel.postUiState.collectAsState()
    val jobUiState by jobViewModel.jobUiState.collectAsState()
    val state by viewModel.state.collectAsState()


    // 🔥 MAIN LAYOUT
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // 🔹 PROFILE SECTION
        when (val result = state) {

            is SeekerProfileState.Loading -> {
                Text("Loading profile...")
            }

            is SeekerProfileState.Success -> {
                val profile = result.data

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Welcome : ${profile.full_name}")
                        Text(text = "State: ${profile.state}")
                    }
                }
            }

            is SeekerProfileState.Error -> {
                Text("Error: ${result.message}")
            }

            else -> {}
        }

        Spacer(modifier = Modifier.height(16.dp))



        // 🔹 POSTS SECTION
        when (postUiState) {

            is PostUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is PostUiState.Success -> {
                val posts = (postUiState as PostUiState.Success).posts
                PostHorizontalList(posts = posts)
            }

            is PostUiState.Error -> {
                Text(
                    text = (postUiState as PostUiState.Error).message,
                    color = Color.Red
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))




        when(jobUiState){

            is JobUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is JobUiState.Success -> {

                val jobs = (jobUiState as JobUiState.Success).jobs
                JobHorizontalList(
                    jobs = jobs,
                    onViewClick = {},
                    mainNavController = mainNavController
                )
            }

            is JobUiState.Error -> {
                Text(
                    text = (jobUiState as JobUiState.Error).message,
                    color = Color.Red
                )
            }

        }

    }

}