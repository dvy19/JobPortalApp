package com.example.jobportal.recruiterScreens.blog

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jobportal.auth.SessionManager
import com.example.jobportal.recruiterScreens.post.CreatePostUiState
import com.example.jobportal.recruiterScreens.post.PostRepository
import com.example.jobportal.recruiterScreens.post.PostViewModel
import com.example.jobportal.recruiterScreens.post.PostViewModelFactory
import com.example.jobportal.recruiter_details.RecruiterDetailsViewModel
import com.example.jobportal.recruiter_details.RecruiterDetailsViewModelFactory
import com.example.jobportal.recruiter_details.RecruiterProfileRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen() {
    // State variables to hold the user input


    val context=LocalContext.current

    val sessionManager= SessionManager(context)
    val repository=PostRepository(sessionManager)
    val viewModel: PostViewModel = viewModel(
        factory = PostViewModelFactory(repository)
    )

    var showDialog by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val maxCharLimit = 500

    LaunchedEffect(uiState) {


        if (uiState is CreatePostUiState.Success) {
            showDialog = true

            // ✅ Clear fields
            title = ""
            description = ""

            viewModel.resetState() // VERY IMPORTANT
        }
    }

    if (uiState is CreatePostUiState.Loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator() // Everything inside the Box is now centered
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    // Optional: navigate here if needed
                    // navController.navigate("PostList")
                }) {
                    Text("Close")
                }
            },
            title = { Text("Success") },
            text = { Text("Post created successfully!") }
        )
    }
    // Main container - fills the whole screen and is NOT scrollable
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .statusBarsPadding() // Avoids overlapping with the phone's clock/status bar
    ) {
        // --- Header Section ---
        Text(
            text = "New Post",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- Title Input ---
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Post Title") },
            placeholder = { Text("Enter a catchy title...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = MaterialTheme.shapes.medium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = description,
            onValueChange = {
                if (it.length <= maxCharLimit) description = it
            },
            label = { Text("Content") },
            placeholder = { Text("Write your story here...") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Works only if inside a Column or Row
            shape = MaterialTheme.shapes.medium,

            // CORRECT WAY to handle multi-line/scrolling:
            singleLine = false,
            minLines = 5 // Optional: sets a minimum height
            // maxLines is not set so it can grow/scroll infinitely within the weight(1f)
        )

        // --- Character Counter & Helper ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "${description.length} / $maxCharLimit",
                style = MaterialTheme.typography.labelMedium,
                color = if (description.length >= maxCharLimit) Color.Red else Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Submit Button ---
        Button(
            onClick = {
                viewModel.createPost(title,description)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = MaterialTheme.shapes.large,
            enabled = title.isNotBlank() && description.isNotBlank()
        ) {
            Text("Publish Post", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview
@Composable
fun PreviewPostScreen(){
    PostScreen()
}