package com.example.jobportal.blog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogScreen() {
    // State variables to hold the user input
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val maxCharLimit = 500

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
            label = { Text("Blog Title") },
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
            onClick = { /* Handle Submit Logic Here */ },
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
fun PreviewBlogScreen(){
    BlogScreen()
}