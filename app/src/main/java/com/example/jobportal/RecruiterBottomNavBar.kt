package com.example.jobportal


import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.IconButton
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun RecruiterBottomNavBar(navController: NavHostController) {

    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route

    BottomAppBar {

        IconButton(
            onClick = {
                navController.navigate(Screens.RecruiterHome.route)
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                Icons.Default.Home,
                contentDescription = null,
                tint = if (currentRoute == Screens.RecruiterHome.route)
                    Color(0xFF6C2BEE) else Color.Gray
            )
        }

        IconButton(
            onClick = {
                navController.navigate(Screens.RecruiterPost.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                tint = if (currentRoute == Screens.RecruiterPost.route)
                    Color(0xFF6C2BEE) else Color.Gray
            )
        }

        IconButton(
            onClick = {
                navController.navigate(Screens.CreateBlog.route)
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                Icons.Default.Message,
                contentDescription = null,
                tint = if (currentRoute == Screens.CreateBlog.route)
                    Color(0xFF6C2BEE) else Color.Gray
            )
        }

        IconButton(
            onClick = {
                navController.navigate(Screens.RecruiterJob.route)
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                Icons.Default.Message,
                contentDescription = null,
                tint = if (currentRoute == Screens.RecruiterJob.route)
                    Color(0xFF6C2BEE) else Color.Gray
            )
        }

        IconButton(
            onClick = {
                navController.navigate(Screens.RecruiterProfile.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                tint = if (currentRoute == Screens.RecruiterProfile.route)
                    Color(0xFF6C2BEE) else Color.Gray
            )
        }
    }
}
