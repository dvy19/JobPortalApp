package com.example.jobportal.seeekerScreens.profile


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jobportal.Screens
import com.example.jobportal.auth.SessionManager
import com.example.jobportal.recruiterScreens.profile.ProfileScreen
import com.example.jobportal.recruiter_details.RecruiterDetailsViewModel
import com.example.jobportal.recruiter_details.RecruiterDetailsViewModelFactory
import com.example.jobportal.recruiter_details.RecruiterProfileRepository
import com.example.jobportal.recruiter_details.RecruiterProfileState
import com.example.jobportal.seeekerScreens.details.SeekerDetailsViewModel
import com.example.jobportal.seeekerScreens.details.SeekerDetailsViewModelFactory
import com.example.jobportal.seeekerScreens.details.SeekerProfileRepository
import com.example.jobportal.seeekerScreens.details.SeekerProfileState

@Composable
fun ProfileScreen(
    mainNavController: NavController
){

    val context = LocalContext.current

    val backgroundColor = Color(0xFFF8FAFC)
    val cardColor = Color.White
    val primaryText = Color(0xFF1E293B)
    val secondaryText = Color(0xFF64748B)

    val sessionManager = SessionManager(context)
    val repository = SeekerProfileRepository(sessionManager)
    val viewModel: SeekerDetailsViewModel = viewModel(
        factory = SeekerDetailsViewModelFactory(repository)
    )

    LaunchedEffect(Unit) {
        viewModel.getSeekerProfile()
    }

    val state = viewModel.state.collectAsState()



    when (val result = state.value) {

        is SeekerProfileState.Idle -> {
            // Initial state (before API call)
            Text("Welcome")
        }

        is SeekerProfileState.Loading -> {
            Text("Loading profile...")
        }

        is SeekerProfileState.Success -> {
            val profile = result.data

            SeekerProfileLayout(
                name=profile.full_name,
                city=profile.city,
                mainNavController = mainNavController
            )


        }

        is SeekerProfileState.Error -> {
            Text(text = "Error: ${result.message}")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeekerProfileLayout(
    name:String,
    city:String,
    mainNavController: NavController

) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Profile", fontWeight = FontWeight.SemiBold) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // --- Header Section: Profile Pic, Name, City ---
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                // Replace with your actual image resource or AsyncImage for URLs
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize().padding(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = city,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- Action Buttons ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { /* Edit Profile */ },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Edit Profile")
                }

                OutlinedButton(
                    onClick = { /* Logout */ },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Log Out")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- Options List ---
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ProfileOptionItem(
                    "Personal Details",
                    Icons.Default.Info,
                    onClick ={ mainNavController.navigate(Screens.SeekerSkill.route)   }
                )
                ProfileOptionItem("Resume", Icons.Default.Description)
                ProfileOptionItem("Skills", Icons.Default.Star)
                ProfileOptionItem("More", Icons.Default.MoreHoriz) }
        }
    }
}

@Composable
fun ProfileOptionItem(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit = {}) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Navigate */ },
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline
            )
        }
    }
}