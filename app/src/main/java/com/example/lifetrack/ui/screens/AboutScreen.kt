package com.example.lifetrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "About LifeTrack",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = Color(0xFF2E5EAA)
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF2E5EAA)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFF8F9FA)
                )
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // App Icon
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF4A90E2)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.MedicalServices,
                    contentDescription = "App Icon",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // App Title
            Text(
                text = "LifeTrack Health Companion",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color(0xFF2E5EAA),
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tagline
            Text(
                text = "Your complete health management solution",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color(0xFF6C757D),
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // About Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // About Section
                    Text(
                        text = "About LifeTrack",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color(0xFF2E5EAA)
                        )
                    )
                    Text(
                        text = "LifeTrack is a cutting-edge, comprehensive health management app designed to empower you with seamless control over your medical history, appointments, and wellness journey.",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Divider(
                        color = Color(0xFFE9ECEF),
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Features Section
                    Text(
                        text = "Key Features",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color(0xFF2E5EAA)
                        )
                    )

                    FeatureItem(
                        iconColor = Color(0xFF4A90E2),
                        title = "Medical Timeline & Records",
                        description = "Organize and access your medical history with ease"
                    )
                    FeatureItem(
                        iconColor = Color(0xFF4A90E2),
                        title = "Telemedicine Appointments",
                        description = "Connect with healthcare professionals from home"
                    )
                    FeatureItem(
                        iconColor = Color(0xFF4A90E2),
                        title = "Medication Reminders",
                        description = "Never miss a dose with customizable alerts"
                    )
                    FeatureItem(
                        iconColor = Color(0xFF4A90E2),
                        title = "Emergency Alerts",
                        description = "Instantly notify contacts in urgent situations"
                    )
                    FeatureItem(
                        iconColor = Color(0xFF4A90E2),
                        title = "Health Information Hub",
                        description = "Access reliable health resources and articles"
                    )

                    Divider(
                        color = Color(0xFFE9ECEF),
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Developer Section
                    Text(
                        text = "Developed By",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color(0xFF2E5EAA)
                        )
                    )
                    Text(
                        text = "LifeTrack Health Solutions",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Innovating for Your Well-Being",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontStyle = FontStyle.Italic,
                            color = Color(0xFF6C757D)
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Contact Section
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFFF1F8FE),
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Contact Us:",
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = Color(0xFF2E5EAA)
                            )
                        )
                        Text(
                            text = "contact@lifetrack.example.com",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "www.lifetrack.example.com",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Footer
            Text(
                text = "© 2023 LifeTrack. All rights reserved.",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF6C757D)
                )
            )
        }
    }
}

@Composable
private fun FeatureItem(
    iconColor: Color,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(iconColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E5EAA)
                )
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF6C757D)
                )
            )
        }
    }
}