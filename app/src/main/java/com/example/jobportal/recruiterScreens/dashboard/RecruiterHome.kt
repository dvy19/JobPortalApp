package com.example.jobportal.recruiterScreens.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jobportal.auth.SessionManager
import com.example.jobportal.recruiterScreens.post.PostResponse
import com.example.jobportal.recruiter_details.RecruiterDetailsViewModel
import com.example.jobportal.recruiter_details.RecruiterDetailsViewModelFactory
import com.example.jobportal.recruiter_details.RecruiterProfileRepository
import com.example.jobportal.recruiter_details.RecruiterProfileState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import com.example.jobportal.recruiterScreens.post.PostRepository
import com.example.jobportal.recruiterScreens.post.PostUiState
import com.example.jobportal.recruiterScreens.post.PostViewModel
import com.example.jobportal.recruiterScreens.post.PostViewModelFactory
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.jobportal.recruiterScreens.job.JobRepository
import com.example.jobportal.recruiterScreens.job.JobResponse
import com.example.jobportal.recruiterScreens.job.JobUiState
import com.example.jobportal.recruiterScreens.job.JobViewModel
import com.example.jobportal.recruiterScreens.job.JobViewModelFactory

@Composable
fun RecruiterHome(
    mainNavController: NavController
) {
    val context = LocalContext.current

    val sessionManager = SessionManager(context)
    val repository = RecruiterProfileRepository(sessionManager)
    val viewModel: RecruiterDetailsViewModel = viewModel(
        factory = RecruiterDetailsViewModelFactory(repository)
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
        viewModel.getRecruiterProfile()
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

            is RecruiterProfileState.Loading -> {
                Text("Loading profile...")
            }

            is RecruiterProfileState.Success -> {
                val profile = result.data

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "City: ${profile.city}")
                        Text(text = "State: ${profile.state}")
                    }
                }
            }

            is RecruiterProfileState.Error -> {
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
                    onViewClick = {}
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

@Composable
fun PostHorizontalList(
    posts: List<PostResponse>,
    onLikeClick: (PostResponse) -> Unit = {},
    onCommentClick: (PostResponse) -> Unit = {}
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(posts) { post ->

            PostItem(
                post = post,
                onLikeClick = { onLikeClick(post) },
                onCommentClick = { onCommentClick(post) }
            )
        }
    }
}

@Composable
fun PostItem(
    post: PostResponse,
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {

            // 🔹 User Info
            Text(
                text = post.full_name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            if (!post.company_name.isNullOrEmpty()) {
                Text(
                    text = post.company_name,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 🔹 Title
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // 🔹 Description
            Text(
                text = post.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 Actions Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                // ❤️ Like Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onLikeClick() }
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Like"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "${post.likes_count}")
                }

                // 💬 Comment Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onCommentClick() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Comment,
                        contentDescription = "Comment"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Comment")
                }
            }
        }
    }
}



@Composable
fun JobHorizontalList(
    jobs: List<JobResponse>,
    onViewClick: () -> Unit,
    //onCommentClick: (JobResponse) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(jobs) { job ->

            JobItem(
                job= job,
                onViewClick = {  },
                //onCommentClick = { onCommentClick(job) }
            )
        }
    }
}

@Composable
fun JobItem(
    job: JobResponse,
    onViewClick: () -> Unit = {},
    //onCommentClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {

            // 🔹 User Info
            Text(
                text = job.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )


                Text(
                    text = job.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )


            Spacer(modifier = Modifier.height(8.dp))

            // 🔹 Title
            Text(
                text = job.description,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // 🔹 Description
            Text(
                text = job.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 Actions Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                // ❤️ Like Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onViewClick() }
                ) {
                    Button(
                        onClick = { onViewClick() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = "View")
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    //Text(text = "${post.likes_count}")
                }
            }
        }
    }
}