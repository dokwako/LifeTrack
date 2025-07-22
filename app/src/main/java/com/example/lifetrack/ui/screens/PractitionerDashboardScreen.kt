package com.example.lifetrack.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PractitionerDashboardScreen(navController: NavController) {
    // Mock Patient Data
    val patient = remember {
        Patient(
            id = "LT997654321",
            name = "Emma Johnson",
            age = 45,
            gender = "Female",
            bloodPressure = "190/120",
            lastVisit = "April 26, 2024",
            condition = "Hypertensive Crisis"
        )
    }

    // Mock Chart Data
    val bpData = remember {
        sortedMapOf(
            Date(System.currentTimeMillis() - 6 * 86400000L) to 150f,
            Date(System.currentTimeMillis() - 5 * 86400000L) to 145f,
            Date(System.currentTimeMillis() - 4 * 86400000L) to 160f,
            Date(System.currentTimeMillis() - 3 * 86400000L) to 170f,
            Date(System.currentTimeMillis() - 2 * 86400000L) to 190f,
            Date(System.currentTimeMillis() - 86400000L) to 185f,
            Date() to 180f
        )
    }

    val medicationAdherence = remember {
        mapOf(
            "Lisinopril" to 85f,
            "Metformin" to 92f,
            "Aspirin" to 78f
        )
    }

    // Visit History
    val visits = remember {
        listOf(
            VisitRecord(
                date = "April 15, 2024",
                diagnosis = "Hypertension Checkup",
                medications = "Lisinopril 10mg daily"
            ),
            VisitRecord(
                date = "March 28, 2024",
                diagnosis = "Diabetes Management",
                medications = "Metformin 500mg twice daily"
            )
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            patient.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            "ID: ${patient.id}",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Export */ }) {
                        Icon(Icons.Default.Share, "Export")
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
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Critical Alert
            item {
                AlertCard(
                    title = "CRITICAL HYPERTENSION",
                    message = "Current BP: ${patient.bloodPressure} mmHg",
                    actions = {
                        TextButton(onClick = { /* Call */ }) {
                            Text("CALL PATIENT")
                        }
                        TextButton(onClick = { /* Acknowledge */ }) {
                            Text("ACKNOWLEDGE")
                        }
                    }
                )
            }

            // Patient Summary
            item {
                MedicalCard(title = "PATIENT SUMMARY") {
                    PatientInfoRow(label = "Age/Gender", value = "${patient.age}y • ${patient.gender}")
                    PatientInfoRow(label = "Blood Type", value = "A+")
                    PatientInfoRow(label = "Allergies", value = "Penicillin")
                    PatientInfoRow(label = "Conditions", value = patient.condition)
                }
            }

            // Blood Pressure Chart
            item {
                MedicalCard(title = "BLOOD PRESSURE TREND") {
                    BloodPressureChart(
                        systolicData = bpData,
                        diastolicData = bpData.mapValues { it.value - 30f }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        MetricBadge(value = patient.bloodPressure, label = "Current", isCritical = true)
                        MetricBadge(value = "+12%", label = "Trend")
                        MetricBadge(value = "3 days", label = "Since Last Med")
                    }
                }
            }

            // Medication Adherence
            item {
                MedicalCard(title = "MEDICATION ADHERENCE") {
                    MedicationAdherenceChart(adherenceData = medicationAdherence)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        medicationAdherence.forEach { (med, percent) ->
                            AdherencePill(name = med, percentage = percent.toInt())
                        }
                    }
                }
            }

            // Visit History
            item {
                MedicalCard(title = "VISIT HISTORY") {
                    visits.forEachIndexed { index, visit ->
                        if (index > 0) Divider(modifier = Modifier.padding(vertical = 8.dp))
                        VisitRecordItem(visit)
                    }
                }
            }

            // Quick Actions
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ActionButton(icon = Icons.Default.Medication, label = "Prescribe")
                    ActionButton(icon = Icons.Default.Science, label = "Labs")
                    ActionButton(icon = Icons.Default.CalendarToday, label = "Schedule")
                }
            }
        }
    }
}

// ===== Chart Components =====

@Composable
private fun BloodPressureChart(
    systolicData: Map<Date, Float>,
    diastolicData: Map<Date, Float>
) {
    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                description.isEnabled = false
                legend.isEnabled = true

                val systolicEntries = systolicData.map {
                    Entry(it.key.time.toFloat(), it.value)
                }
                val diastolicEntries = diastolicData.map {
                    Entry(it.key.time.toFloat(), it.value)
                }

                data = LineData(
                    LineDataSet(systolicEntries, "Systolic").apply {
                        color = Color(0xFFE53935).toArgb()
                        lineWidth = 2.5f
                        setDrawCircles(true)
                        circleRadius = 4f
                        setDrawValues(false)
                        mode = LineDataSet.Mode.CUBIC_BEZIER
                    },
                    LineDataSet(diastolicEntries, "Diastolic").apply {
                        color = Color(0xFF1E88E5).toArgb()
                        lineWidth = 2.5f
                        setDrawCircles(true)
                        circleRadius = 4f
                        setDrawValues(false)
                        mode = LineDataSet.Mode.CUBIC_BEZIER
                    }
                )

                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    granularity = 86400000f // 1 day
                    valueFormatter = object : ValueFormatter() {
                        private val format = SimpleDateFormat("MMM dd")
                        override fun getFormattedValue(value: Float) =
                            format.format(Date(value.toLong()))
                    }
                }

                axisLeft.apply {
                    axisMinimum = 50f
                    axisMaximum = 200f
                    addLimitLine(LimitLine(140f, "Normal").apply {
                        lineColor = Color(0xFF4CAF50).toArgb()
                        lineWidth = 1f
                    } )
                }

                axisRight.isEnabled = false
                animateX(1000)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}

@Composable
private fun MedicationAdherenceChart(adherenceData: Map<String, Float>) {
    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                description.isEnabled = false
                legend.isEnabled = false

                val entries = adherenceData.values.mapIndexed { index, value ->
                    BarEntry(index.toFloat(), value)
                }

                val dataSet = BarDataSet(entries, "Adherence").apply {
                    colors = listOf(
                        Color(0xFF4CAF50).toArgb(),
                        Color(0xFFFFC107).toArgb(),
                        Color(0xFFF44336).toArgb()
                    )
                    valueTextColor = Color.Black.toArgb()
                }

                val data = BarData(dataSet)
                xAxis.apply {
                    valueFormatter = object : ValueFormatter() {
                        override fun getFormattedValue(value: Float) =
                            adherenceData.keys.elementAtOrNull(value.toInt()) ?: ""
                    }
                    position = XAxis.XAxisPosition.BOTTOM
                    granularity = 1f
                }

                axisLeft.axisMaximum = 100f
                axisRight.isEnabled = false
                animateY(1000)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

// ===== UI Components =====

@Composable
private fun MedicalCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun AlertCard(
    title: String,
    message: String,
    actions: @Composable RowScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFEBEE)
        ),
        border = BorderStroke(1.dp, Color(0xFFE53935))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = Color(0xFFE53935),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color(0xFFE53935),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = message)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                actions()
            }
        }
    }
}

@Composable
private fun PatientInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun MetricBadge(
    value: String,
    label: String,
    isCritical: Boolean = false
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(
                    color = if (isCritical) Color(0x30E53935) else MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                )
                .size(48.dp)
        ) {
            Text(
                text = value,
                color = if (isCritical) Color(0xFFE53935) else MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
private fun AdherencePill(
    name: String,
    percentage: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = when {
                        percentage > 90 -> Color(0xFF4CAF50)
                        percentage > 75 -> Color(0xFFFFC107)
                        else -> Color(0xFFF44336)
                    },
                    shape = CircleShape
                )
        ) {
            Text(
                text = "$percentage%",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun VisitRecordItem(record: VisitRecord) {
    Column {
        Text(
            text = record.date,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = record.diagnosis,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = record.medications,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
private fun ActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.width(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = { /* TODO */ },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

// ===== Data Classes =====

private data class Patient(
    val id: String,
    val name: String,
    val age: Int,
    val gender: String,
    val bloodPressure: String,
    val lastVisit: String,
    val condition: String
)

private data class VisitRecord(
    val date: String,
    val diagnosis: String,
    val medications: String
)