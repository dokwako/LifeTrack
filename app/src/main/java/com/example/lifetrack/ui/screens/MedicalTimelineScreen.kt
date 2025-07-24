package com.example.lifetrack.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import java.time.LocalDate

data class MedicalVisit(
    val id: Int,
    val date: LocalDate,
    val diagnosis: String,
    val treatment: String,
    val notes: String,
    val doctor: String,
    val hospital: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicalTimelineScreen(navController: NavController) {
    var isVisible by remember { mutableStateOf(false) }

    val visits = listOf(
        MedicalVisit(
            id = 1,
            date = LocalDate.of(2025, 6, 15),
            diagnosis = "Upper Respiratory Infection",
            treatment = "Antibiotics (Amoxicillin), Rest",
            notes = "Follow-up recommended in 2 weeks",
            doctor = "Hilary Otieno",
            hospital = "Nakuru General Hospital"
        ),
        MedicalVisit(
            id = 2,
            date = LocalDate.of(2025, 7, 10),
            diagnosis = "Mild Hypertension",
            treatment = "Lifestyle changes, Monitor BP",
            notes = "Referral to cardiologist pending",
            doctor = "Mercy Baraka",
            hospital = "Rift Valley Provincial Hospital"
        ),
        MedicalVisit(
            id = 3,
            date = LocalDate.of(2025, 7, 20),
            diagnosis = "Allergic Rhinitis",
            treatment = "Antihistamines, Avoid allergens",
            notes = "Symptoms improved, continue treatment",
            doctor = "Tabitha Kerry",
            hospital = "Kabarak Mission Hospital"
        ),
        MedicalVisit(
            id = 4,
            date = LocalDate.of(2025, 7, 25),
            diagnosis = "Vitamin D Deficiency",
            treatment = "Supplements, Sun Exposure",
            notes = "Revisit in one month",
            doctor = "Hilary Otieno",
            hospital = "Nakuru General Hospital"
        ),
        MedicalVisit(
            id = 5,
            date = LocalDate.of(2025, 7, 28),
            diagnosis = "Minor Laceration",
            treatment = "Stitches, Antibiotics",
            notes = "Keep wound clean, follow-up if infected",
            doctor = "Mercy Baraka",
            hospital = "Rift Valley Provincial Hospital"
        )
    )

    LaunchedEffect(Unit) {
        delay(300)
        isVisible = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Medical Timeline",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(600))
                ) {
                    Text(
                        text = "Medical History",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                }
            }
            items(visits) { visit ->
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(700 + visit.id * 100))
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.DateRange,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = visit.date.toString(),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider()
                            Text(
                                text = "Diagnosis: ${visit.diagnosis}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Treatment: ${visit.treatment}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Notes: ${visit.notes}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.LocalHospital,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = MaterialTheme.colorScheme.tertiary
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Reviewed by ${visit.doctor}, ${visit.hospital}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(1000))
                ) {
                    Text(
                        text = "End of Timeline",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}