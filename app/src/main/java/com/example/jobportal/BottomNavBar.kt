package com.example.jobportal


import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


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
