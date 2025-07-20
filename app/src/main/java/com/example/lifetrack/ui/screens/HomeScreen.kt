package com.example.lifetrack.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.example.lifetrack.ui.components.HealthSummaryCard
import com.example.lifetrack.ui.components.LifeTrackTopBar
import com.example.lifetrack.ui.components.QuickActionsRow

@Composable
fun HomeScreen(
    navController: NavController,
    onEmergency: () -> Unit,
    onSearch: () -> Unit,
    onAlma: () -> Unit
) {
    var refresh by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500)
        isVisible = true
        refresh = true
    }

    Scaffold(
        topBar = {
            LifeTrackTopBar(
                onMenuClick = { navController.navigate("menu") },
                onProfileClick = { navController.navigate("profile") }
            )
        },
        containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.95f)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp), // Increased spacing
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            item {
//                AnimatedVisibility(
//                    visible = isVisible,
//                    enter = fadeIn(animationSpec = tween(1000))
//                ) {
//                    Text(
//                        text = "Welcome Back!",
//                        style = MaterialTheme.typography.headlineSmall,
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.padding(bottom = 14.dp)
//                    )
//                }
//            }
            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(1200))
                ) {
                    HealthSummaryCard()
                }
            }

            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(1400))
                ) {
//                    Text(
//                        text = "Quick Actions",
//                        style = MaterialTheme.typography.titleLarge,
//                        modifier = Modifier.padding(bottom = 16.dp)
//                    )
                    QuickActionsRow(
                        onEmergencyClick = onEmergency,
                        onSearchClick = onSearch,
                        onAlmaClick = onAlma
                    )
                }
            }

            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(1600))
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .wrapContentHeight(),
                        elevation = CardDefaults.cardElevation(8.dp),
                        onClick = { navController.navigate("login") }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CalendarToday,
                                contentDescription = "Medical Timeline",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Medical Timeline",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Sample: Dr. Jane Doe - General Checkup",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(1800))
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .wrapContentHeight(),
                        elevation = CardDefaults.cardElevation(8.dp),
                        onClick = { navController.navigate("login") }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocalHospital,
                                contentDescription = "Telemedicine",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Telemedicine",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Connect with a doctor now",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(2000))
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .wrapContentHeight(),
                        elevation = CardDefaults.cardElevation(8.dp),
                        onClick = { navController.navigate("login") }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Notifications,
                                contentDescription = "Epidemic Alert",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Epidemic Alert",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "HIV/TB Treatment Camps",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(2200))
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .wrapContentHeight(),
                        elevation = CardDefaults.cardElevation(8.dp),
                        onClick = { navController.navigate("login") }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.People,
                                contentDescription = "Practitioner Connect",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Practitioner Connect",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Find help nearby",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(2400))
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .wrapContentHeight(),
                        elevation = CardDefaults.cardElevation(8.dp),
                        onClick = { navController.navigate("login") }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Info Hub",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Info Hub",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Missions & Security Info",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(2600))
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .wrapContentHeight(),
                        elevation = CardDefaults.cardElevation(8.dp),
                        onClick = { navController.navigate("login") }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Additional Features",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Free HIV Test Alerts (Kenya)",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Bamboo Health Points (China)",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Group Care",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(2800))
                ) {
                    Button(
                        onClick = { navController.navigate("login") },
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(top = 24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Get Started", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
    }
}