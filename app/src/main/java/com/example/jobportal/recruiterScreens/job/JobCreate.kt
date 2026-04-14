package com.example.jobportal.recruiterScreens.job

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jobportal.auth.SessionManager
import com.example.jobportal.recruiterScreens.blog.BlogRepository
import com.example.jobportal.recruiterScreens.blog.BlogViewModel
import com.example.jobportal.recruiterScreens.blog.BlogViewModelFactory
import com.example.jobportal.recruiterScreens.blog.CreateBlogUiState
import kotlinx.coroutines.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobCreate() {


    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var stipend by remember { mutableStateOf("") }
    val context=LocalContext.current

    val sessionManager= SessionManager(context)
    val repository=JobRepository(sessionManager)
    val viewModel: JobViewModel = viewModel(
        factory = JobViewModelFactory(repository)
    )



    var showDialog by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()


    LaunchedEffect(uiState) {


        if (uiState is CreateJobUiState.Success) {
            showDialog = true

            // ✅ Clear fields
            title=""
            description=""
            location=""
            stipend=""



            viewModel.resetState() // VERY IMPORTANT
        }
    }

    if (uiState is CreateJobUiState.Loading) {
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
                    // navController.navigate("blogList")
                }) {
                    Text("Close")
                }
            },
            title = { Text("Success") },
            text = { Text("Job created successfully!") }
        )
    }



    // Light Theme Palette: Soft Blue and White
    val customColors = lightColorScheme(
        primary = Color(0xFF0061A4), // Deep Professional Blue
        secondary = Color(0xFF535F70),
        surface = Color(0xFFFDFBFF),
        onSurfaceVariant = Color(0xFF44474E)
    )

    MaterialTheme(colorScheme = customColors) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start
            ) {
                // Header Section
                Text(
                    text = "Post a New Job",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = "Fill in the details below to find your perfect candidate.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp, bottom = 32.dp)
                )

                // Input Fields Section
                CustomOutlinedField(
                    value = title,
                    onValueChange = { title = it },
                    label = "Job Title",
                    placeholder = "e.g. Senior Android Developer",
                    icon = Icons.Default.Work
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Job Description") },
                    placeholder = { Text("Describe the roles, responsibilities, and perks...") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 4,
                    shape = MaterialTheme.shapes.medium
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomOutlinedField(
                    value = location,
                    onValueChange = { location = it },
                    label = "Location",
                    placeholder = "e.g. Remote or City, Country",
                    icon = Icons.Default.LocationOn
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomOutlinedField(
                    value = stipend,
                    onValueChange = { stipend = it },
                    label = "Stipend / Salary",
                    placeholder = "e.g. ₹25,000 - ₹40,000",
                    icon = Icons.Default.CurrencyRupee,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Submit Button
                Button(
                    onClick = {
                        viewModel.createJob(title,description,location,stipend.toFloat())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = MaterialTheme.shapes.large,
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    Icon(Icons.Default.AddCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Post Job Listing",
                        style = MaterialTheme.typography.titleMedium.copy(letterSpacing = 1.sp)
                    )
                }
            }
        }
    }
}

@Composable
fun CustomOutlinedField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        keyboardOptions = keyboardOptions
    )
}

@Preview
@Composable
fun PreviewJob(){
    JobCreate()
}