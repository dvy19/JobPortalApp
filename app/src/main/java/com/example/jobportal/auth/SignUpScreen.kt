package com.example.jobportal.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    rootNavController: NavController,
    viewModel: RegisterViewModel = viewModel()) {

    var isRegister=viewModel.registerState.value

    val context=LocalContext.current

    val sessionManager= SessionManager(context)

    val role=sessionManager.getRole()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Dropdown state
    val roles = listOf("job_seeker", "recruiter")
    var expanded by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf(roles[0]) }

    val state = viewModel.registerState.value

    Column(
        modifier = Modifier.padding(24.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Register", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ✅ DROPDOWN FOR ROLE
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {

            OutlinedTextField(
                value = selectedRole,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Role") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                roles.forEach { role ->
                    DropdownMenuItem(
                        text = { Text(role) },
                        onClick = {
                            selectedRole = role
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.register(email, password, selectedRole)
                println("Button Clicked")
                println("Email: $email, Password: $password")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }

        LaunchedEffect(isRegister) {
            if (isRegister) {

                if(selectedRole=="job_seeker"){
                    rootNavController.navigate("seekerDetails")
                }
               else if (selectedRole == "recruiter"){
                    rootNavController.navigate("recruiterDetails") {
                        popUpTo("signup") { inclusive = true }
                    }
                }
            }
        }



        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Preview
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen(rootNavController = rememberNavController())

}