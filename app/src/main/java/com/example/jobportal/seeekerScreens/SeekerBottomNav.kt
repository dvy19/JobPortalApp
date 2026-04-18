package com.example.jobportal.seeekerScreens


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
import com.example.jobportal.Screens


@Composable
fun SeekerBottomNav(mainNavController: NavHostController) {

    val currentRoute =
        mainNavController.currentBackStackEntryAsState().value?.destination?.route

    BottomAppBar {

        IconButton(
            onClick = {
                mainNavController.navigate(Screens.SeekerDashboard.route)
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                Icons.Default.Home,
                contentDescription = null,
                tint = if (currentRoute == Screens.SeekerDashboard.route)
                    Color(0xFF6C2BEE) else Color.Gray
            )
        }

        IconButton(
            onClick = {
                mainNavController.navigate(Screens.SeekerPost.route) {
                    popUpTo(mainNavController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                tint = if (currentRoute == Screens.SeekerPost.route)
                    Color(0xFF6C2BEE) else Color.Gray
            )
        }

        IconButton(
            onClick = {
                mainNavController.navigate(Screens.SeekerBlog.route)
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                Icons.Default.Message,
                contentDescription = null,
                tint = if (currentRoute == Screens.SeekerBlog.route)
                    Color(0xFF6C2BEE) else Color.Gray
            )
        }



        IconButton(
            onClick = {
                mainNavController.navigate(Screens.SeekerProfile.route) {
                    popUpTo(mainNavController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                tint = if (currentRoute == Screens.SeekerProfile.route)
                    Color(0xFF6C2BEE) else Color.Gray
            )
        }
    }
}