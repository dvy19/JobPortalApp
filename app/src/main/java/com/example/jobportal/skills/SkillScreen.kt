package com.example.jobportal.skills

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jobportal.auth.SessionManager
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@Composable
fun SkillScreen(
    mainNavController: NavController
){

    val context = LocalContext.current

    val backgroundColor = Color(0xFFF8FAFC)
    val cardColor = Color.White
    val primaryText = Color(0xFF1E293B)
    val secondaryText = Color(0xFF64748B)


    val sessionManager = SessionManager(context)
    val repository = SkillRepository(sessionManager)
    val viewModel: SkillViewModel = viewModel(
        factory = SkillFactory(repository)
    )

    val createState by viewModel.state.collectAsState()
    val skillState by viewModel.skillState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getSkill()
    }

    var skillText by remember { mutableStateOf("") }


    val state = viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 🔹 Input Field
        OutlinedTextField(
            value = skillText,
            onValueChange = { skillText = it },
            label = { Text("Enter Skill") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 🔹 Submit Button
        Button(
            onClick = {
                if (skillText.isNotBlank()) {
                    viewModel.createSkill(AddSkillRequest(skillText))
                    skillText = ""
                    viewModel.getSkill() // refresh list
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Skill")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 Skill List Section
        when (skillState) {

            is GetSkillState.Loading -> {
                CircularProgressIndicator()
            }

            is GetSkillState.Success -> {
                val skills = (skillState as GetSkillState.Success).data

                LazyColumn {
                    items(skills) { skill ->
                        SkillItem(skill)
                    }
                }
            }

            is GetSkillState.Error -> {
                Text(
                    text = (skillState as GetSkillState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }

            else -> {}
        }
    }
}

@Composable
fun SkillItem(skill: Skill) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = skill.name,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
fun PreviewSkill(){
    SkillScreen(mainNavController = rememberNavController())

}