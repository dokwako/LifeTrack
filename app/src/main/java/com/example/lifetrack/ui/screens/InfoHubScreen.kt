package com.example.lifetrack.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import java.time.LocalDate

data class MissionItem(
    val id: Int,
    val title: String,
    val description: String,
    val status: String,
    val icon: ImageVector,
    val securityLevel: String,
    val date: String,
    val location: String,
    val dateObj: LocalDate
)

data class HealthTip(
    val id: Int,
    val title: String,
    val description: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoHubScreen(navController: NavController) {
    var isVisible by remember { mutableStateOf(false) }
    val currentDate = LocalDate.now()

    val allMissions = listOf(
        MissionItem(
            id = 1,
            title = "Malaria Eradication Campaign",
            description = "Community-wide initiative to distribute mosquito nets and provide education on prevention.",
            status = "Upcoming",
            icon = Icons.Default.MedicalServices,
            securityLevel = "Medium",
            date = "1 Aug - 15 Aug 2025",
            location = "Nairobi County",
            dateObj = LocalDate.of(2025, 8, 1)
        ),
        MissionItem(
            id = 2,
            title = "Child Vaccination Drive",
            description = "Free polio and measles vaccination for children under 5 across multiple regions.",
            status = "Upcoming",
            icon = Icons.Default.ChildCare,
            securityLevel = "High",
            date = "20 Aug - 5 Sep 2025",
            location = "All Counties",
            dateObj = LocalDate.of(2025, 8, 20)
        ),
        MissionItem(
            id = 3,
            title = "Annual Health Check-Up",
            description = "Free comprehensive health screening for all community members with mobile units.",
            status = "Upcoming",
            icon = Icons.Default.HealthAndSafety,
            securityLevel = "Low",
            date = "10 Sep - 25 Sep 2025",
            location = "Central Region",
            dateObj = LocalDate.of(2025, 9, 10)
        ),
        MissionItem(
            id = 4,
            title = "Nutrition Awareness Program",
            description = "Workshops on balanced diets and healthy eating habits for all age groups.",
            status = "Upcoming",
            icon = Icons.Default.Restaurant,
            securityLevel = "Low",
            date = "1 Oct - 15 Oct 2025",
            location = "Eastern Region",
            dateObj = LocalDate.of(2025, 10, 1)
        ),
        MissionItem(
            id = 5,
            title = "HIV Testing Initiative",
            description = "Free testing and counseling services with mobile clinics in rural areas.",
            status = "Upcoming",
            icon = Icons.Default.Favorite,
            securityLevel = "Medium",
            date = "20 Oct - 5 Nov 2025",
            location = "Western Region",
            dateObj = LocalDate.of(2025, 10, 20)
        )
    )

    val missions = allMissions.filter {
        it.status in listOf("Active", "Upcoming") && (it.status == "Active" || it.dateObj.isAfter(currentDate))
    }

    val healthTips = listOf(
        HealthTip(
            id = 1,
            title = "Stay Hydrated",
            description = "Drink at least 8 glasses of water daily to maintain body functions.",
            icon = Icons.Default.LocalDrink
        ),
        HealthTip(
            id = 2,
            title = "Regular Exercise",
            description = "30 minutes of moderate exercise daily improves cardiovascular health.",
            icon = Icons.Default.DirectionsRun
        ),
        HealthTip(
            id = 3,
            title = "Balanced Diet",
            description = "Include fruits, vegetables, and whole grains in every meal for optimal nutrition.",
            icon = Icons.Default.Restaurant
        ),
        HealthTip(
            id = 4,
            title = "Mental Wellness",
            description = "Practice mindfulness or meditation for 10 minutes daily to reduce stress.",
            icon = Icons.Default.SelfImprovement
        ),
        HealthTip(
            id = 5,
            title = "Regular Check-Ups",
            description = "Schedule annual health screenings to detect issues early.",
            icon = Icons.Default.HealthAndSafety
        )
    )

    LaunchedEffect(Unit) {
        delay(300)
        isVisible = true
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Community Health Hub") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            item {
                HeaderSection(
                    title = "Upcoming Health Campaigns",
                    isVisible,
                    subtitle = "Stay informed about community health initiatives"
                )
            }
            items(missions) { mission ->
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(600))
                ) {
                    MissionCard(mission = mission)
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                HeaderSection(
                    title = "Health Tips",
                    isVisible,
                    delay = 800,
                    subtitle = "Daily recommendations for better health"
                )
            }
            items(healthTips) { tip ->
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(800))
                ) {
                    HealthTipCard(tip = tip)
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Last Updated: ${LocalDate.now()}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            }
        }
    }
}

@Composable
fun HeaderSection(
    title: String,
    visible: Boolean,
    delay: Int = 600,
    subtitle: String
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(delay))
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth() // Re-added with proper context
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun MissionCard(mission: MissionItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = mission.icon,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = mission.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = mission.status,
                        style = MaterialTheme.typography.labelSmall,
                        color = when (mission.status) {
                            "Active" -> MaterialTheme.colorScheme.primary
                            "Upcoming" -> MaterialTheme.colorScheme.tertiary
                            "Planned" -> MaterialTheme.colorScheme.secondary
                            else -> MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
                SecurityBadge(mission.securityLevel)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = mission.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoChip(
                    icon = Icons.Default.DateRange,
                    text = mission.date,
                    iconColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
                InfoChip(
                    icon = Icons.Default.LocationOn,
                    text = mission.location,
                    iconColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun HealthTipCard(tip: HealthTip) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = tip.icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = tip.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = tip.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun SecurityBadge(level: String) {
    Badge(
        containerColor = when (level) {
            "High" -> MaterialTheme.colorScheme.errorContainer
            "Medium" -> MaterialTheme.colorScheme.tertiaryContainer
            else -> MaterialTheme.colorScheme.secondaryContainer
        },
        contentColor = when (level) {
            "High" -> MaterialTheme.colorScheme.onErrorContainer
            "Medium" -> MaterialTheme.colorScheme.onTertiaryContainer
            else -> MaterialTheme.colorScheme.onSecondaryContainer
        }
    ) {
        Text(text = level, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun InfoChip(
    icon: ImageVector,
    text: String,
    iconColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = iconColor
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = textColor
        )
    }
}