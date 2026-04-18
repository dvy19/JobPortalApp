package com.example.jobportal.seeekerScreens.details

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.jobportal.Screens
import com.example.jobportal.auth.SessionManager
import com.example.jobportal.recruiterScreens.job.JobRequest
import com.example.jobportal.recruiter_details.CustomTextField
import com.example.jobportal.recruiter_details.RecruiterDetailsViewModel
import com.example.jobportal.recruiter_details.RecruiterDetailsViewModelFactory
import com.example.jobportal.recruiter_details.RecruiterProfileRepository
import com.example.jobportal.recruiter_details.RecruiterProfileState
import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import java.util.Calendar

@Composable
fun SeekerDetailsScreen(
    rootNavController: NavController
) {
    val pureWhite = Color(0xFFFFFFFF)
    val lightGray = Color(0xFFF2F2F2)
    val accentBlack = Color(0xFF111111)

    var fullName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var collegeName by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var stateLoc by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf<String?>(null) }

    val context=LocalContext.current

    val sessionManager= SessionManager(context)
    val repository=SeekerProfileRepository(sessionManager)
    val viewModel: SeekerDetailsViewModel = viewModel(
        factory = SeekerDetailsViewModelFactory(repository)
    )

    val state by viewModel.state.collectAsState()
    val blueShade = Color(0xFF90CAF9) // subtle light blue

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(Color(0xFFF5F9FF)), // very light background
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(

            text = "Create Profile",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1565C0) // deep blue accent
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Fill in your details to get started",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Full Name
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),

        )
        Spacer(modifier = Modifier.height(16.dp))

        // Date of Birth

        DOBInputField(
            dob = dob,
            onDateSelected = { dob = it }
        )


        Spacer(modifier = Modifier.height(16.dp))

        // College Name
        OutlinedTextField(
            value = collegeName,
            onValueChange = { collegeName = it },
            label = { Text("College Name") },
            modifier = Modifier.fillMaxWidth(),

        )
        Spacer(modifier = Modifier.height(16.dp))

        // City & State Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("City") },
                modifier = Modifier.weight(1f),

            )
            OutlinedTextField(
                value = stateLoc,
                onValueChange = { stateLoc = it },
                label = { Text("State") },
                modifier = Modifier.weight(1f),

            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Gender Selection Cards
        Text(
            text = "Select Gender",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1565C0)
            )
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            listOf("male", "female", "Other").forEach { gender ->
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { selectedGender = gender },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedGender == gender) blueShade else Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = gender,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = if (selectedGender == gender) Color.White else Color.Black
                            )
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                val req = SeekerRequest(
                    full_name = fullName,
                    gender = selectedGender.toString(),
                    city = city,
                    state = stateLoc,
                    college_name = collegeName,
                    date_of_birth = dob )

                Log.d("button clicked", req.toString())
                Log.d("result", state.toString())

                viewModel.createSeekerProfile(req) },

            modifier = Modifier .fillMaxWidth() .height(54.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black) )
        { Text("Submit", color = Color.White, fontWeight = FontWeight.Medium) }
    }



    LaunchedEffect(state) {
            when (state) {
                is SeekerProfileState.Success -> {
                    rootNavController.navigate(Screens.SeekerMain.route)

                    Log.d("messae","succesfull")
                }



                is SeekerProfileState.Error -> {

                    Log.d("messae", (state as SeekerProfileState.Error).message)

                }

                else -> {
                    Log.d("messae","loading")
                }
            }
        }
    }


@Composable
fun MinimalField(placeholder: String, modifier: Modifier = Modifier) {
    var value by remember { mutableStateOf("") }

    TextField(
        value = value,
        onValueChange = { value = it },
        placeholder = { Text(placeholder, color = Color.Gray, fontSize = 14.sp) },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF9F9F9),
            unfocusedContainerColor = Color(0xFFF9F9F9),
            disabledContainerColor = Color(0xFFF9F9F9),
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(8.dp)
    )
}

@SuppressLint("DefaultLocale")
@Composable
fun DOBInputField(
    dob: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current

    OutlinedTextField(
        value = dob,
        onValueChange = { }, // disable manual typing
        label = { Text("Date of Birth (YYYY-MM-DD)") },
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Show DatePickerDialog when clicked
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                DatePickerDialog(
                    context,
                    { _, selectedYear, selectedMonth, selectedDay ->
                        // Format as YYYY-MM-DD
                        val formatted = String.format(
                            "%04d-%02d-%02d",
                            selectedYear,
                            selectedMonth + 1,
                            selectedDay
                        )
                        onDateSelected(formatted)
                    },
                    year,
                    month,
                    day
                ).show()
            },
        readOnly = true, // prevent keyboard input

    )
}
@Preview
@Composable
fun SeekerDetailsScreenPreview() {
    SeekerDetailsScreen(rootNavController = rememberNavController())
}