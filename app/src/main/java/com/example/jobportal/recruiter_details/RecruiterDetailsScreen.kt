package com.example.jobportal.recruiter_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jobportal.auth.SessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecruiterDetailsScreen(

    rootNavController: NavController
) {


    val context=LocalContext.current

    val sessionManager= SessionManager(context)
    val repository=RecruiterProfileRepository(sessionManager)
    val viewModel: RecruiterDetailsViewModel = viewModel(
        factory = RecruiterDetailsViewModelFactory(repository)
    )

    val state by viewModel.state.collectAsState()
    val recruiterState by viewModel.state.collectAsState()
    // State variables for each input field
    var companyName by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var stateLoc by remember { mutableStateOf("") }

    // Logic to check if all fields are populated




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Job Portal",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3852B4),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Add Your Details ",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3852B4),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Reusable Input Fields
        CustomTextField(
            value = companyName,
            onValueChange = { companyName = it },
            label = "Company Name",
            icon = Icons.Default.Business)

        CustomTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = "Full Name",
            icon = Icons.Default.Person)

        CustomTextField(
            value = position,
            onValueChange = { position = it },
            label = "Position",
            icon = Icons.Default.Person)

        CustomTextField(
            value = city,
            onValueChange = { city = it },
            label = "City",
            icon = Icons.Default.LocationOn)

        CustomTextField(
            value = stateLoc,
            onValueChange = { stateLoc = it },
            label = "State",
            icon = Icons.Default.Home)

        Spacer(modifier = Modifier.height(24.dp))

        // Submit Button
        Button(
            onClick = {
                val request = RecruiterProfileRequest(
                    company_name = companyName,
                    full_name = fullName,
                    position = position,
                    city = city,
                    state = stateLoc
                )

                viewModel.createRecruiterProfile(request)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = companyName.isNotBlank() &&
                    fullName.isNotBlank() &&
                    position.isNotBlank() &&
                    city.isNotBlank() &&
                    stateLoc.isNotBlank(), // Button is only active if logic is true
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Submit Details", fontSize = 18.sp)
        }



        LaunchedEffect(state) {
            when (state) {



                is RecruiterProfileState.Success -> {
                    rootNavController.navigate("main") {

                    }
                }

                is RecruiterProfileState.Error -> {
                    // optional: show toast/snackbar
                }

                else -> {}
            }
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(imageVector = icon, contentDescription = null) },
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        )
    )
}

@Preview
@Composable
fun RecruiterDetailsScreenPreview() {
    RecruiterDetailsScreen(rootNavController = rememberNavController())
}