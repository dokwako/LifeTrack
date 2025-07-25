package com.example.lifetrack.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontStyle
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
import com.example.lifetrack.model.data.VisitRecord
import com.example.lifetrack.model.data.Patient
import com.example.lifetrack.model.data.LabTest
import com.example.lifetrack.model.data.Prescription
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.lifetrack.model.data.Message

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpertScreen(navController: NavController) {
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

    // Mock Lab Tests
    val labTests = remember {
        listOf(
            LabTest(
                name = "Complete Blood Count",
                date = "Apr 20, 2024",
                results = mapOf(
                    "WBC" to "6.5 (Normal)",
                    "RBC" to "4.2 (Normal)",
                    "Hemoglobin" to "12.8 (Low)"
                )
            ),
            LabTest(
                name = "Lipid Panel",
                date = "Apr 15, 2024",
                results = mapOf(
                    "Cholesterol" to "210 (High)",
                    "Triglycerides" to "150 (Borderline)"
                )
            )
        )
    }

    // Prescription History
    val prescriptions = remember {
        listOf(
            Prescription(
                medication = "Lisinopril 10mg",
                dosage = "Once daily",
                duration = "30 days",
                notes = "For blood pressure control"
            ),
            Prescription(
                medication = "Metformin 500mg",
                dosage = "Twice daily",
                duration = "90 days",
                notes = "With meals"
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
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
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

            // Lab Results
            item {
                MedicalCard(title = "LAB RESULTS") {
                    labTests.forEach { test ->
                        LabTestItem(test)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            // Prescriptions
            item {
                MedicalCard(title = "PRESCRIPTIONS") {
                    prescriptions.forEach { prescription ->
                        PrescriptionItem(prescription)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
            item {
                MedicalCard(title = "PATIENT COMMUNICATION") {
                    MockChatPanel()
                }
            }
        }
    }
}
@Composable
private fun MockChatPanel() {
    val mockMessages = remember {
        listOf(
            Message("1", "I've been having headaches", true, "10:30 AM"),
            Message("2", "Any fever or dizziness?", false, "10:32 AM"),
            Message("3", "No fever but some dizziness", true, "10:35 AM")
        )
    }

    var newMessage by remember { mutableStateOf(TextFieldValue("")) }

    Column {
        // Message History
        LazyColumn(
            modifier = Modifier
                .height(200.dp)
                .padding(8.dp)
        ) {
            items(mockMessages) { message ->  // Fixed: Using items correctly
                MessageBubble(message = message)  // Fixed: Proper parameter name
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        // Input Area
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = newMessage,
                onValueChange = { newMessage = it },
                modifier = Modifier
                    .weight(1f)
                    .background(Color.LightGray.copy(alpha = 0.2f))
                    .padding(12.dp),
                decorationBox = { innerTextField ->
                    if (newMessage.text.isEmpty()) {
                        Text(
                            "Type message...",
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                }
            )

            IconButton(
                onClick = { newMessage = TextFieldValue("") },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    Icons.Default.Send,
                    contentDescription = "Send",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun MessageBubble(message: Message) {  // Fixed: Proper parameter definition
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        contentAlignment = if (message.isFromPatient) Alignment.CenterStart else Alignment.CenterEnd
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (message.isFromPatient)
                    Color(0xFFE3F2FD)
                else
                    Color(0xFFDCEDC8)
            )
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = message.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
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
                    })
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
private fun LabTestItem(test: LabTest) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = test.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = test.date,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column {
                test.results.forEach { (name, value) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = value,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = when {
                                value.contains("High") -> Color(0xFFE53935)
                                value.contains("Low") -> Color(0xFFFFA000)
                                else -> MaterialTheme.colorScheme.onSurface
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PrescriptionItem(prescription: Prescription) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = prescription.medication,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Dosage: ${prescription.dosage}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Duration: ${prescription.duration}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (prescription.notes.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Notes: ${prescription.notes}",
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}