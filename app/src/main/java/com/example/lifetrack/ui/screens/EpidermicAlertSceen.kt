package com.example.lifetrack.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lifetrack.R
import kotlinx.coroutines.delay

// Define standard alert colors
val CriticalAlert = Color(0xFFD32F2F)
val HighAlert = Color(0xFFFFA000)
val MediumAlert = Color(0xFFFFC107)
val LowAlert = Color(0xFF388E3C)

data class EpidemicAlert(
    val id: Int,
    val title: String,
    val location: String,
    val severity: String, // "Critical", "High", "Medium", "Low"
    val date: String,
    val description: String,
    val precautions: List<String>,
    val status: String, // "Active", "Contained", "New"
    val imageUrl: String = "",
    val localImageRes: Int = R.drawable.ic_medical_placeholder
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpidemicAlertScreen(navController: NavController) {
    var isVisible by remember { mutableStateOf(false) }

    val epidemicAlerts = listOf(
        EpidemicAlert(
            id = 1,
            title = "Malaria Outbreak",
            location = "Nairobi County",
            severity = "High",
            date = "15 Nov - 30 Dec 2023",
            description = "Increased cases reported due to heavy rainfall and mosquito breeding",
            precautions = listOf(
                "Use mosquito nets",
                "Apply insect repellent",
                "Seek immediate treatment for symptoms"
            ),
            status = "Active",
            localImageRes = R.drawable.malaria
        ),
        EpidemicAlert(
            id = 2,
            title = "Cholera Alert",
            location = "Coastal Region",
            severity = "Critical",
            date = "1 Dec - Present",
            description = "Waterborne outbreak detected in informal settlements",
            precautions = listOf(
                "Drink boiled or treated water",
                "Maintain proper sanitation",
                "Oral rehydration for symptoms"
            ),
            status = "New",
            localImageRes = R.drawable.who
        ),
        EpidemicAlert(
            id = 3,
            title = "COVID-19 Variant",
            location = "Nationwide",
            severity = "Medium",
            date = "Ongoing",
            description = "New variant detected with increased transmissibility",
            precautions = listOf(
                "Get booster shots",
                "Wear masks in crowds",
                "Monitor for symptoms"
            ),
            status = "Active",
            localImageRes = R.drawable.covid
        ),
        EpidemicAlert(
            id = 4,
            title = "General Health Alert",
            location = "Multiple Regions",
            severity = "Low",
            date = "Ongoing",
            description = "Seasonal health advisory for common illnesses",
            precautions = listOf(
                "Practice good hygiene",
                "Stay hydrated",
                "Visit health centers if symptoms persist"
            ),
            status = "Contained",
            localImageRes = R.drawable.alerts
        )
    )

    LaunchedEffect(Unit) {
        delay(300)
        isVisible = true
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Epidemic Alerts", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(600))
                ) {
                    AlertSummaryCard()
                }
            }

            items(epidemicAlerts) { alert ->
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(600 + alert.id * 150))
                ) {
                    EpidemicAlertCard(alert = alert)
                }
            }
        }
    }
}

@Composable
fun AlertSummaryCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "Current Health Alerts",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Using your alerts.jpg for the summary banner
            Image(
                painter = painterResource(R.drawable.alerts),
                contentDescription = "Health Alert Banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AlertStatItem("4", "Active Alerts", Icons.Default.NotificationImportant)
                AlertStatItem("1", "Critical", Icons.Default.PriorityHigh)
                AlertStatItem("3", "Regions", Icons.Default.Map)
            }
        }
    }
}

@Composable
fun EpidemicAlertCard(alert: EpidemicAlert) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Using your downloaded images for each alert
            Image(
                painter = painterResource(alert.localImageRes),
                contentDescription = "${alert.title} visual",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    alert.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Badge(
                    containerColor = when(alert.severity) {
                        "Critical" -> CriticalAlert
                        "High" -> HighAlert
                        "Medium" -> MediumAlert
                        else -> LowAlert
                    },
                    contentColor = Color.White
                ) {
                    Text(alert.severity)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(alert.location)

                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(alert.date)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                alert.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Precautions:",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Column(modifier = Modifier.padding(top = 8.dp)) {
                alert.precautions.forEach { precaution ->
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MediumAlert
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(precaution)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Status: ${alert.status}",
                color = when(alert.status) {
                    "Active" -> CriticalAlert
                    "New" -> HighAlert
                    else -> MediumAlert
                },
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AlertStatItem(count: String, label: String, icon: ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(count, fontWeight = FontWeight.Bold)
        Text(label, style = MaterialTheme.typography.labelSmall)
    }
}