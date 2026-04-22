package com.example.jobportal.seeekerScreens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jobportal.auth.SessionManager
import com.example.jobportal.recruiterScreens.job.JobRepository
import com.example.jobportal.recruiterScreens.job.JobViewModel
import com.example.jobportal.recruiterScreens.job.JobViewModelFactory
import com.example.jobportal.recruiterScreens.job.SingleJobUiState
import com.example.jobportal.seeekerScreens.apply.ApplyJobFactory
import com.example.jobportal.seeekerScreens.apply.ApplyJobRepository
import com.example.jobportal.seeekerScreens.apply.ApplyJobUiState
import com.example.jobportal.seeekerScreens.apply.ApplyJobViewModel
import com.example.jobportal.seeekerScreens.details.SeekerDetailsViewModel
import com.example.jobportal.seeekerScreens.details.SeekerDetailsViewModelFactory
import com.example.jobportal.seeekerScreens.details.SeekerProfileRepository

@Composable
fun JobDisplay(
    id: String?,
    mainNavController: NavController
) {



    val context=LocalContext.current

    val sessionManager= SessionManager(context)
    val jobRepo= JobRepository(sessionManager)
    val jobViewModel: JobViewModel=viewModel(
        factory = JobViewModelFactory(jobRepo)
    )



    LaunchedEffect(id) {
        id?.toInt()?.let {
            jobViewModel.fetchSingleJob(it)
        }
    }

    LaunchedEffect(Unit) {
        jobViewModel.fetchJob()
    }

    val jobUiState by jobViewModel.jobUiState.collectAsState()
    val singleJobUiState by jobViewModel.singleJobUiState.collectAsState()

    val state = jobViewModel.singleJobUiState.collectAsState().value

    when(state){

        is SingleJobUiState.Loading -> {
            Text("Loading...")
        }

        is SingleJobUiState.Success->{
            val singleJob=state.job
            JobDisplayLayout(
                title=singleJob.title,
                description=singleJob.description,
                skills=singleJob.skill_names,
                location=singleJob.location,
                createAt = singleJob.created_at,
                jobId = singleJob.id
            )
        }

        is SingleJobUiState.Error -> {
            Text(text = state.message)
        }

    }


    // Mock Data
    //val skills = listOf("Kotlin", "Jetpack Compose", "Material 3", "MVVM", "Coroutines")



}


@Composable
fun JobDisplayLayout(
    jobId:Int,
    title:String,
    description:String,
    skills:List<String>,
    //scrollState: ScrollState,
    location:String,
    createAt:String


){
    val context=LocalContext.current

    val sessionManager= SessionManager(context )
    val applyJobRepo= ApplyJobRepository(sessionManager)
    val applyViewModel: ApplyJobViewModel=viewModel (
        factory = ApplyJobFactory(applyJobRepo)
    )

    val applyState= applyViewModel.applyJobState.collectAsState().value


    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Button(
                    onClick = {
                        if (applyState !is ApplyJobUiState.Loading) {
                            applyViewModel.apply_job(jobId)
                        }
                    },
                    enabled = applyState !is ApplyJobUiState.Loading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        when (applyState) {
                            is ApplyJobUiState.Error -> "Error ${applyState.message}"

                            is ApplyJobUiState.Loading -> "Applying..."
                            is ApplyJobUiState.Success -> "Applied ✅"
                            else -> "Apply Now"
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(20.dp)
        ) {
            // Header: Icon and Company Info
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(64.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Business,
                        contentDescription = null,
                        modifier = Modifier.padding(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = createAt,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Divider(thickness = 0.5.dp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(24.dp))

            // Section: Description
            Text(
                text = "Job Description",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 24.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Section: Skills
            Text(
                text = "Required Skills",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            // FlowRow equivalent using standard Row + Modifier.wrapContentHeight for simplicity
            // In modern Compose, use ContextualFlowRow or similar for complex wrapping
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    skills.take(3).forEach { skill ->
                        SkillChip(skill)
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    skills.drop(3).forEach { skill ->
                        SkillChip(skill)
                    }
                }
            }
        }
    }
}

@Composable
fun SkillChip(label: String) {
    SuggestionChip(
        onClick = { },
        label = { Text(label) },
        shape = RoundedCornerShape(8.dp),
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            labelColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    )
}