package com.example.lifetrack.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lifetrack.ui.components.HealthSummaryCard
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import com.example.lifetrack.ui.components.QuickActionsRow
import androidx.core.net.toUri
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(navController: NavController) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by  bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "LifeTrack",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
//                        textAlign = Alignment.CenterVertically
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("menu") }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = MaterialTheme.colorScheme.primary)
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Filled.Person, contentDescription = "Profile", tint = MaterialTheme.colorScheme.primary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon (Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = currentRoute == "Home",
                    onClick = { bottomNavController.navigate("home") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Info, contentDescription = "Help & Support") },
                    label = { Text("Help & Support") },
                    selected = currentRoute == "Help & Support",
                    onClick = { bottomNavController.navigate("Help & Support") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.MedicalServices, contentDescription = "Services") },
                    label = { Text("Services") },
                    selected = currentRoute == "Services",
                    onClick = { bottomNavController.navigate("Services") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Transact") },
                    label = { Text("Transact") },
                    selected = currentRoute == "transact",
                    onClick = { bottomNavController.navigate("transact") }
                )
            }
        },


        containerColor = asColor()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            HealthSummaryCard()

//            Text(
//                text = "Welcome Back ",
//                style = MaterialTheme.typography.titleLargeEmphasized,
//                color = MaterialTheme.colorScheme.primary,
//                modifier = Modifier.align(Alignment.Start),
//                fontWeight = FontWeight.Bold
//            )
            //QuickActionsRow(onEmergencyClick = { onEmergencyCall(context) }, onSearchClick = { navController.navigate("help_support")}, onAlmaClick = {navController.navigate("chat")})

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    GlassActionCard(
                        icon = Icons.Filled.CalendarToday,
                        title = "Medical Timeline",
                        subtitle = "My Medical History",
                        onClick = { navController.navigate("medical_timeline") }
                    )
                }
//                item {
//                    GlassActionCard(
//                        icon = Icons.Filled.LocalHospital,
//                        title = "Telemedicine",
//                        subtitle = "Connect with a doctor now",
//                        onClick = { navController.navigate("telemedicine") }
//                    )
//                }
                item {
                    GlassActionCard(
                        icon = Icons.Filled.Notifications,
                        title = "Public Health Alert",
                        subtitle = "HIV/TB Treatment Camps",
                        onClick = { navController.navigate("epidemic_alert") }
                    )
                }
//                item {
//                    GlassActionCard(
//                        icon = Icons.Filled.People,
//                        title = "Practitioner Connect",
//                        subtitle = "Find help nearby",
//                        onClick = { navController.navigate("expert") }
//                    )
//                }
                item {
                    GlassActionCard(
                        icon = Icons.Filled.Info,
                        title = "Community Health Hub",
                        subtitle = "Health Campaign and awareness",
                        onClick = { navController.navigate("info_hub") }
                    )
                }
                item {
                    GlassActionCard(
                        icon = Icons.Filled.DeviceThermostat,
                        title = "Appointments",
                        subtitle = "Upcoming v (Kenya)",
                        onClick = { navController.navigate("additional_features") }
                    )
                }
                item {
                    GlassActionCard(
                        icon = Icons.AutoMirrored.Filled.Help,
                        title = "Help & Support",
                        subtitle = "FAQs, Contact Us",
                        onClick = {
                            navController.navigate("help_support")
                        }
                    )

                }
                item {
                    GlassActionCard(
                       icon = Icons.Filled.LocalPharmacy,
                        title = "Medications ",
                        subtitle = "Prescriptions and Reminders",
                        onClick = {
                            navController.navigate("help_support")
                        }
                    )
                }

//                item {
//                    GlassActionCard(
//                        icon = Icons.Filled.Healing,
//                        title = "BHP",
//                        subtitle = "Bamboo Health Points (China)",
//                        onClick = {
//                            navController.navigate("bhp")
//                        }
//                    )
//                }
            }
        }
    }
}

@Composable
fun GlassActionCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    val shape: Shape = RoundedCornerShape(20.dp)
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(shape)
//            .blur(1.dp)
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                    )
                ),
                shape = shape
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
            )
        }
    }
}


fun asColor(): Color = Color.Transparent

//@Composable
private fun onEmergencyCall(context: android.content.Context) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = "tel:112".toUri()
    }
    context.startActivity(intent)
}