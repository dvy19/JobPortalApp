package com.example.jobportal.recruiterScreens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
fun RecruiterProfile(
    mainNavController: NavController
) {

    val context = LocalContext.current

    val backgroundColor = Color(0xFFF8FAFC)
    val cardColor = Color.White
    val primaryText = Color(0xFF1E293B)
    val secondaryText = Color(0xFF64748B)

    val sessionManager = SessionManager(context)
    val repository = RecruiterProfileRepository(sessionManager)
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

            ProfileScreen(
                fullName = profile.full_name,
                companyName = profile.company_name,
                position = profile.position,
                city = profile.city,
                state = profile.state

            )


        }

        is RecruiterProfileState.Error -> {
            Text(text = "Error: ${result.message}")
        }
    }

}

@Composable
fun ProfileScreen(
    fullName:String,
     companyName:String,
    position:String,
    city:String,
     state:String
) {
    // Light Theme Palette
    val backgroundColor = Color(0xFFF8FAFC)
    val cardColor = Color.White
    val primaryText = Color(0xFF1E293B)
    val secondaryText = Color(0xFF64748B)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // --- Profile Photo ---
        Surface(
            modifier = Modifier.size(120.dp),
            shape = CircleShape,
            color = Color.LightGray,
            shadowElevation = 8.dp
        ) {
            // Replace with AsyncImage for real URLs
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile Picture",
                modifier = Modifier.padding(20.dp),
                tint = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Name & Email ---
        Text(
            text = fullName,
            style = MaterialTheme.typography.headlineMedium,
            color = primaryText,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "testRandom@design.com",
            style = MaterialTheme.typography.bodyLarge,
            color = secondaryText
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- Information Card ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                InfoRow(icon = Icons.Default.Business, label = "Company", value =companyName)
                Divider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)
                InfoRow(icon = Icons.Default.LocationOn, label = "Location", value = city)
                InfoRow(icon = Icons.Default.LocationOn, label = "Location", value = state)

            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF1E293B),
                fontWeight = FontWeight.Medium
            )
        }
    }
}
